package com.levio.cil.requestsimulator.ui.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import com.levio.cil.requestsimulator.ui.UICONSTANTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RequestSimulatorCt implements Initializable {

	@FXML
	private TextField walletAddress;

	@FXML
	private TextField contractAddress;

	@FXML
	private TextArea details;

	@FXML
	private Label resultMessage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void sendRequest() {

		sendPostRequest(walletAddress.getText(), contractAddress.getText(), details.getText());

		this.walletAddress.clear();
		this.contractAddress.clear();
		this.details.clear();
	}

	private void sendPostRequest(String walletAddress, String contractAddress, String details) {

		try {

			URL url = new URL(UICONSTANTS.API_URL_POST);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod(UICONSTANTS.POST);
			connection.setRequestProperty(UICONSTANTS.CONTENT_TYPE, UICONSTANTS.AJSON);

			String requestPayload = "{\r\n" + "   \"clientContractAddress\":\"" 
					+ contractAddress 
					+ "\",\r\n" + "   \"clientWalletAddress\":\"" 
					+ walletAddress 
					+ "\",\r\n" + "   \"note\":\"" 
					+ details
					+ "\"\r\n" + "}";

			OutputStream stream = connection.getOutputStream();
			stream.write(requestPayload.getBytes());
			stream.flush();

			printServiceResponse(connection);

			connection.disconnect();

			this.resultMessage.setText("Request Sent!");

		} catch (Exception e) {

			this.resultMessage.setText("Technical Error!");
		}
	}

	private void printServiceResponse(HttpURLConnection connection) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");

		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
	}

	@FXML
	public void quit() {
		System.exit(0);
	}
}