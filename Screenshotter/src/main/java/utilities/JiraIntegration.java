package utilities;

/*************************************************************************

 * *

 * Author: Subhayan Bakshi *

 * *

 *************************************************************************/

import java.io.File;

import com.mashape.unirest.http.HttpResponse;

import com.mashape.unirest.http.Unirest;

import io.restassured.path.json.JsonPath;

//import kong.unirest.core.HttpResponse;

//import kong.unirest.core.Unirest;

public class JiraIntegration {

	// JiraIntegration

	// Declare the status variable as public static

	public static boolean status;

	public void attachWordDoc2Jira(String docFileName, String textStringJiraID) throws Exception {

		System.out.println("JIRA ID====>" + textStringJiraID);

		//////////////////////// Extracting contents from Config.json

		// Parse the JSON content using JsonPath

		String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\Config.json";

		System.out.println("filePath====>" + filePath);

		// Create JsonPath object from the file path

		JsonPath jsonPath = new JsonPath(new File(filePath));

		String domainName = jsonPath.getString("domain_name").trim();

		// String issueIdOrKey = jsonPath.getString("issueIdOrKey").trim();

		String issueIdOrKey = textStringJiraID;

		String userid = jsonPath.getString("userid").trim();

		String docFilePath = System.getProperty("user.dir") + "/" + docFileName + ".docx";

		String apiKey = jsonPath.getString("apiKey").trim();

		String endpoint = "https://" + domainName + ".atlassian.net/rest/api/2/issue/" + issueIdOrKey + "/attachments";

		System.out.println("Endpoint :" + endpoint);

		try {

			HttpResponse response = Unirest.post(endpoint).basicAuth(userid, apiKey)

					.header("Accept", "application/json").header("X-Atlassian-Token", "no-check")

					.field("file", new File(docFilePath)).asJson();

			System.out.println(response.getBody());

			if (response.getBody().toString()

					.contains("Issue does not exist or you do not have permission to see it.")) {

				status = false;

			} else {

				status = true;

				System.out.println("Successfully uploaded to Jira!");

			}

		} catch (Exception e) {

			status = false;

			System.out.println("Failed to upload to Jira!");

		}

	}

	public void uploadToJiraCustom(String docFileName, String textStringJiraID) throws Exception {

		//////////////////////// Extracting contents from Config.json

		// Parse the JSON content using JsonPath

		String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\Config.json";

		System.out.println("filePath-===" + filePath);

		// Create JsonPath object from the file path

		JsonPath jsonPath = new JsonPath(new File(filePath));

		String domainName = jsonPath.getString("domain_name").trim();

		// String issueIdOrKey = jsonPath.getString("issueIdOrKey").trim();

		String issueIdOrKey = textStringJiraID;

		String userid = jsonPath.getString("userid").trim();

		String docFilePath = docFileName;

		System.out.println(docFilePath);

		String apiKey = jsonPath.getString("apiKey").trim();

		String endpoint = "https://" + domainName + ".atlassian.net/rest/api/2/issue/" + issueIdOrKey + "/attachments";

		System.out.println("Endpoint :" + endpoint);

		try {

			HttpResponse response = Unirest.post(endpoint).basicAuth(userid, apiKey)

					.header("Accept", "application/json").header("X-Atlassian-Token", "no-check")

					.field("file", new File(docFilePath)).asJson();

			System.out.println(response.getBody());

			if (response.getBody().toString()

					.contains("Issue does not exist or you do not have permission to see it.")) {

				status = false;

			} else {

				status = true;

				System.out.println("Successfully uploaded to Jira!");

			}

		} catch (Exception e) {

			status = false;

			System.out.println("Failed to upload to Jira!");

		}

	}

	// Method to get the status value

	public boolean getStatus() {

		return status;

	}

}