package com.fsoft.libms.uitl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.model.Book;


@Component
public class LimsConverter {
	protected static final Logger LOGGER = LoggerFactory.getLogger(LimsConverter.class);
	private static final String ID_NODE = "/id";
	private static final String REASON_NODE = "/reason";
	private static final String CATEGORY_ID_NODE = "/catagory_id";
	private static final String NAME_NODE = "/name";
	private static final String QUANTITY_NODE = "/quantity";
	private static final String AUTHOR_NODE = "/author";
	private static final String PUBLISHER_NODE = "/publisher";
	private static final String CREATE_DATE_NODE = "/createDate";
	private static final String IMAGE_NODE = "/image";
	@Autowired
	private ObjectMapper objMapper;
	
	public long convertToId(String requestData) throws LibMsException {
		long id = -1;
		try {
			JsonNode root = objMapper.readTree(requestData);
			id = root.at(ID_NODE).asLong();
		} catch (IOException e) {
			LOGGER.error("Unable to read input. " + e.getMessage(), e);
			throw new LibMsException("Unable to read input. " + e.getMessage());
		}
		return id;
	}
	
	public Book convertToExercise(String requestData) throws LibMsException {
		Book book = null;
		try {
			JsonNode root = objMapper.readTree(requestData);
			book = getBookFromNode(root);

		} catch (IOException e) {
			LOGGER.error("Unable to read input. " + e.getMessage(), e);
			throw new LibMsException("Unable to read input. " + e.getMessage());
		}
		return book;
	}

	private Book getBookFromNode(JsonNode node) {
		return null;
	}

	public String convertToReason(String data) throws LibMsException {
		String reason = "";
		try {
			JsonNode root = objMapper.readTree(data);
			reason = root.at(REASON_NODE).asText();
		} catch (IOException e) {
			LOGGER.error("Unable to read input. " + e.getMessage(), e);
			throw new LibMsException("Unable to read input. " + e.getMessage());
		}
		return reason;
	}

}
