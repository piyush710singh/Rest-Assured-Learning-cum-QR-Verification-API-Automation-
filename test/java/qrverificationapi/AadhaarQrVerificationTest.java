package qrverificationapi;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AadhaarQrVerificationTest {

    private static final String BASE_URI = System.getProperty(
            "qrverify.baseUri",
            "https://drverify.meon.co.in"
    );

    private static final String ENDPOINT = System.getProperty(
            "qrverify.endpoint",
            "/aadhaar-qr/api/v1/aadhaar/secure-qr/verify-any"
    );

    private static final String IMAGE_DIR_PROPERTY = "qrverify.imageDir";
    private static final String VALID_QR_FILE_PROPERTY = "qrverify.validFile";
    private static final String INVALID_QR_FILE_PROPERTY = "qrverify.invalidFile";
    private static final String CORRUPTED_QR_FILE_PROPERTY = "qrverify.corruptedFile";

    private static final String DEFAULT_IMAGE_DIR = "QRVerificationAPI";
    private static final String DEFAULT_VALID_QR_FILE_NAME = "WhatsApp Image 2026-06-19 at 11.54.44.jpeg";
    private static final String DEFAULT_INVALID_QR_FILE_NAME = "invalid-qr-image.jpeg";
    private static final String DEFAULT_CORRUPTED_QR_FILE_NAME = "WhatsApp Image 2026-06-19 at 12.42.22.jpeg";

    @Test
    public void TC_001_verifyValidAadhaarQrImage() {
        Response response = upload(requiredValidFile());

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("verification_status", equalTo("verified"))
                .body("verification_type", equalTo("aadhaar_any"))
                .body("name", notNullValue())
                .body("gender", notNullValue())
                .body("dob", notNullValue())
                .body("care_of", notNullValue())
                .body("aadhaar_last4", notNullValue())
                .body("reference_id", notNullValue())
                .body("photo_available", equalTo(true))
                .body("address", notNullValue())
                .body("qr_data", notNullValue())
                .body("metadata", notNullValue());
    }

    @Test
    public void TC_002_verifyAadhaarLast4DigitsExtraction() {
        Response response = upload(requiredValidFile());

        response.then().statusCode(200);

        String maskedAadhaar = response.jsonPath().getString("qr_data.masked_aadhaar");
        String aadhaarLast4 = response.jsonPath().getString("aadhaar_last4");

        Assert.assertNotNull(maskedAadhaar, "qr_data.masked_aadhaar should not be null");
        Assert.assertNotNull(aadhaarLast4, "aadhaar_last4 should not be null");
        Assert.assertTrue(maskedAadhaar.length() >= 4, "masked_aadhaar should contain at least 4 characters");
        Assert.assertEquals(aadhaarLast4, maskedAadhaar.substring(maskedAadhaar.length() - 4));
    }

    @Test
    public void TC_003_verifyAddressMapping() {
        Response response = upload(requiredValidFile());

        response.then().statusCode(200);

        Map<String, Object> address = response.jsonPath().getMap("address");
        Assert.assertNotNull(address, "address object should not be null");

        assertPresent(address, "district");
        assertPresent(address, "state");
        assertPresent(address, "pin_code");
        assertPresent(address, "vtc");
        assertPresent(address, "sub_district");
        assertPresent(address, "post_office");
        assertPresent(address, "location");
        assertPresent(address, "landmark");
        assertPresent(address, "house");
        assertPresent(address, "street");
    }

    @Test
    public void TC_004_verifyQrSignatureValidation() {
        Response response = upload(requiredValidFile());

        response.then()
                .statusCode(200)
                .body("metadata.signature_verified", equalTo(true))
                .body("metadata.signature_verification_available", equalTo(true));
    }

    @Test
    public void TC_005_verifyPhotoAvailabilityFlag() {
        Response response = upload(requiredValidFile());

        response.then()
                .statusCode(200)
                .body("photo_available", equalTo(true));
    }

    @Test
    public void TC_006_verifyUnsupportedFileUpload() throws IOException {
        File unsupportedFile = createTempTextFile();

        Response response = upload(unsupportedFile);

        assertClientSideFailure(response);
    }

    @Test
    public void TC_007_verifyMissingFileParameter() {
        Response response = given()
                .baseUri(BASE_URI)
                .contentType(ContentType.MULTIPART)
        .when()
                .post(ENDPOINT);

        assertClientSideFailure(response);
    }

    @Test
    public void TC_008_verifyInvalidQrImage() throws IOException {
        File invalidImage = resolveOptionalScenarioFile(INVALID_QR_FILE_PROPERTY, DEFAULT_INVALID_QR_FILE_NAME);

        Response response = upload(invalidImage);

        assertVerificationFailure(response);
    }

    @Test
    public void TC_009_verifyCorruptedAadhaarQrImage() throws IOException {
        File source = resolveOptionalScenarioFile(CORRUPTED_QR_FILE_PROPERTY, DEFAULT_CORRUPTED_QR_FILE_NAME);
        File corruptedImage = createTruncatedCopy(source);

        Response response = upload(corruptedImage);

        assertVerificationFailure(response);
    }

    @Test
    public void TC_010_verifyResponseSchemaConsistency() {
        Response response = upload(requiredValidFile());

        response.then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(
                        "qrverification/aadhaar-qr-verify-any-schema.json"
                ));
    }

    private Response upload(File file) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.MULTIPART)
                .multiPart("file", file)
        .when()
                .post(ENDPOINT);
    }

    private File requiredValidFile() {
        return resolveScenarioFile(VALID_QR_FILE_PROPERTY, DEFAULT_VALID_QR_FILE_NAME, true);
    }

    private File createTempTextFile() throws IOException {
        File file = File.createTempFile("unsupported-upload-", ".txt");
        file.deleteOnExit();
        Files.writeString(file.toPath(), "This is not an image and should fail validation.");
        return file;
    }

    private File createTruncatedCopy(File source) throws IOException {
        byte[] bytes = Files.readAllBytes(source.toPath());
        int truncatedLength = Math.max(1, bytes.length / 5);

        File file = File.createTempFile("corrupted-qr-", getExtension(source.getName()));
        file.deleteOnExit();
        Files.write(file.toPath(), java.util.Arrays.copyOf(bytes, truncatedLength));
        return file;
    }

    private String getExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return index >= 0 ? fileName.substring(index) : ".bin";
    }

    private File resolveScenarioFile(String systemProperty, String defaultRelativePath, boolean required) {
        File file = resolveConfiguredFile(systemProperty, defaultRelativePath);
        if (file.exists()) {
            return file;
        }

        if (required) {
            throw new SkipException("File not found for " + systemProperty + ": " + file.getPath()
                    + ". Set -" + systemProperty + "=<path-to-file> to point the test at your local image.");
        }

        return file;
    }

    private File resolveOptionalScenarioFile(String systemProperty, String defaultRelativePath) throws IOException {
        File file = resolveConfiguredFile(systemProperty, defaultRelativePath);
        if (file.exists()) {
            return file;
        }

        throw new SkipException("File not found for " + systemProperty + ": " + file.getPath()
                + ". Set -" + systemProperty + "=<path-to-file> or place the file in the image directory.");
    }

    private File resolveConfiguredFile(String systemProperty, String defaultFileName) {
        String configured = System.getProperty(systemProperty);
        if (configured != null && !configured.isBlank()) {
            return new File(configured);
        }

        String configuredDir = System.getProperty(IMAGE_DIR_PROPERTY, DEFAULT_IMAGE_DIR);
        return new File(configuredDir, defaultFileName);
    }

    private void assertPresent(Map<String, Object> map, String key) {
        Assert.assertTrue(map.containsKey(key), key + " should be present");
        Assert.assertNotNull(map.get(key), key + " should not be null");
    }

    private void assertClientSideFailure(Response response) {
        int statusCode = response.statusCode();
        Assert.assertTrue(
                statusCode >= 400 && statusCode < 500,
                "Expected a client-side validation failure, got HTTP " + statusCode
        );
        Assert.assertFalse(response.asString().isBlank(), "Error response should not be empty");
    }

    private void assertVerificationFailure(Response response) {
        int statusCode = response.statusCode();
        Assert.assertTrue(
                (statusCode >= 200 && statusCode < 300) || (statusCode >= 400 && statusCode < 500),
                "Expected graceful verification failure or client-side validation error, got HTTP " + statusCode
        );

        String body = response.asString();
        Assert.assertFalse(body.isBlank(), "Failure response should not be empty");

        if (statusCode >= 400) {
            return;
        }

        String verificationStatus = response.jsonPath().getString("verification_status");
        Assert.assertNotNull(verificationStatus, "verification_status should be present in a graceful verification response");
        Assert.assertNotEquals(
                verificationStatus.trim().toLowerCase(),
                "verified",
                "Expected an unverified response. Response body: " + body
        );

        Assert.assertFalse(response.jsonPath().getBoolean("photo_available"), "photo_available should be false for invalid QR/image input");
        Assert.assertFalse(response.jsonPath().getBoolean("metadata.signature_verified"), "signature_verified should be false for invalid QR/image input");
        Assert.assertFalse(response.jsonPath().getBoolean("metadata.signature_verification_available"), "signature_verification_available should be false for invalid QR/image input");
    }
}
