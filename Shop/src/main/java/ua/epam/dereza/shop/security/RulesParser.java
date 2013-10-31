package ua.epam.dereza.shop.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ua.epam.dereza.shop.bean.User;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class RulesParser {

	private static final Logger log = Logger.getLogger(RulesParser.class);

	String validationSchema;
	String securityFilename;
	List<SecurityRule> rules;

	public RulesParser(String securityFilename, String validationSchema) throws SecurityException {
		rules = new ArrayList<>();
		this.securityFilename = securityFilename;
		this.validationSchema = validationSchema;

		// validates security xml file
		validateSecurityXML();
	}

	public List<SecurityRule> parseXml() throws SecurityException {
		InputStream xmlIns = this.getClass().getResourceAsStream(securityFilename);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		SecurityRule currentRule = null;
		String tagContent = null;

		try {
			XMLStreamReader reader = factory.createXMLStreamReader(xmlIns);
			while (reader.hasNext()) {
				int event = reader.next();
				switch (event) {
				case XMLStreamConstants.START_ELEMENT:
					if ("constraint".equals(reader.getLocalName())) {
						currentRule = new SecurityRule();
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					tagContent = reader.getText().trim();
					break;

				case XMLStreamConstants.END_ELEMENT:
					switch (reader.getLocalName()) {
					case "constraint":
						rules.add(currentRule);
						break;
					case "url-pattern":
						currentRule.setUrlPattern(tagContent);
						break;
					case "role":
						currentRule.addRole(User.Role.valueOf(tagContent.toUpperCase()));
						break;
					}
					break;
				}
			}
		} catch (Exception e){
			throw new SecurityException(e);
		}

		// Print the rules list populated from XML
		if (log.isDebugEnabled())
			log.debug("Security rules ->" + rules);

		return rules;
	}

	public void validateSecurityXML() throws SecurityException {
		try	(InputStream xmlIns = this.getClass().getResourceAsStream(securityFilename);
				InputStream xsdIns = this.getClass().getResourceAsStream(validationSchema)){

			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(xmlIns);
			SchemaFactory schemeFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Source schemaSource = new StreamSource(xsdIns);
			Schema schema = schemeFactory.newSchema(schemaSource);
			Validator validator = schema.newValidator();
			validator.validate(new StAXSource(reader));
		} catch (XMLStreamException e) {
			throw new SecurityException("Cannot find secure xml file", e);
		} catch (SAXException e) {
			throw new SecurityException("Security xml file isn't valid", e);
		} catch (IOException e) {
			throw new SecurityException(e);
		}
	}
}
