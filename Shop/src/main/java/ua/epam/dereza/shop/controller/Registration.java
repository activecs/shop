package ua.epam.dereza.shop.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.RegistrationForm;
import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.service.CaptchaService;
import ua.epam.dereza.shop.service.ImageService;
import ua.epam.dereza.shop.service.UserService;
import ua.epam.dereza.shop.util.BeanTransformer;
import ua.epam.dereza.shop.util.RegistrationValidator;
import ua.epam.dereza.shop.util.ImageUtil;

/**
 * Registration controller
 * 
 * @author Eduard_Dereza
 * 
 */
@WebServlet("/registration")
public class Registration extends HttpServlet {

	private static final long serialVersionUID = 6860202757191222139L;
	private static final Logger log = Logger.getLogger(Registration.class);

	// services
	private UserService userService;
	private CaptchaService captchaService;
	private ImageService imageService;

	// form's fields
	private static final String FORM_FIELD_FIRSTNAME = "firstName";
	private static final String FORM_FIELD_LASTNAME = "lastName";
	private static final String FORM_FIELD_EMAIL = "inputEmail";
	private static final String FORM_FIELD_PASSWORD = "password";
	private static final String FORM_FIELD_BIRTHDAY = "birthDate";
	private static final String FORM_FIELD_COMPANY = "company";
	private static final String FORM_FIELD_ADDRESS1 = "address1";
	private static final String FORM_FIELD_ADDRESS2 = "address2";
	private static final String FORM_FIELD_CITY = "city";
	private static final String FORM_FIELD_POSTCODE = "postcode";
	private static final String FORM_FIELD_ADDITIONAL = "aditionalInfo";
	private static final String FORM_FIELD_PHONE = "phone";
	private static final String FORM_FIELD_CAPTCHA = "captcha";
	private static final String FORM_FIELD_EXPECTED_CAPTCHA = "expectedCaptcha";

	// avatar's additional parameters
	private static final long AVATAR_MAX_SIZE = 5 * 1024 * 1024;
	private String externalResources;

	private String encoding;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		encoding = config.getServletContext().getInitParameter(Constants.ENCODING);
		externalResources = config.getServletContext().getInitParameter(Constants.EXTERNAL_RESOURCES);

		captchaService = (CaptchaService) config.getServletContext().getAttribute(Constants.CAPTCHA_SERVICE);
		userService = (UserService)config.getServletContext().getAttribute(Constants.SERVICE_USER);
		imageService = (ImageService)config.getServletContext().getAttribute(Constants.SERVICE_IMAGE);

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(getServletContext().getInitParameter(Constants.CAPTCHA_MODE).equals(Constants.CAPTCHA_MODE_HIDDEN)){
			String captchaKeyword = captchaService.generateKeyword();
			captchaService.attachCaptcha(captchaKeyword, request, response);
		}

		request.getRequestDispatcher(response.encodeURL(Constants.PAGE_REGISTRATION)).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		User newUser = null;
		RegistrationForm formBean = new RegistrationForm();
		List<String> errors = new ArrayList<String>();
		List<FileItem> avatarContainer = new ArrayList<>();

		// parses request ..>
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				// ..> by apache common uploader
				errors.addAll(parseMultipartRequest(request, formBean, avatarContainer));
			} catch (Exception e) {
				log.error(e);
				errors.add("Promblems with avatar. We allow max avatar size=" + AVATAR_MAX_SIZE + " bytes.");
				// we forwards to registration page because we cannot parse
				// request
				forwardBack(request, response, formBean, errors);

				return;
			}
		}else{
			// ..> by common approach
			errors.addAll(parseSimpleRequest(request, formBean));
		}
		if(log.isEnabledFor(Level.DEBUG))
			log.debug("formBean ->" + formBean);

		// validates captcha
		errors.addAll(captchaService.validateCaptcha(request, formBean));

		// validate formBean
		errors.addAll(new RegistrationValidator().validate(formBean));

		// saves new client
		try{
			if (errors.isEmpty()) {
				newUser = BeanTransformer.transform(formBean);
				if(!userService.isExistUser(newUser.getEmail())){
					// sets default role
					newUser.setRole(User.Role.USER);
					imageService.saveAvatar(avatarContainer.get(0), newUser);
					userService.addUser(newUser);
				}else
					errors.add("User with given email already exist");
			}
		}catch(DAOException e){
			throw new ServletException(e);
		}
		// forward to registration page with determined errors
		if (!errors.isEmpty()) {
			forwardBack(request, response, formBean, errors);
		} else {
			// redirect to main page
			String redirect = null;
			request.getSession().setAttribute(Constants.BEAN_USER, newUser);
			redirect = response.encodeRedirectURL(getServletContext().getContextPath());
			response.sendRedirect(redirect);
		}
	}


	/**
	 * Parses request by apache common file uploader
	 * 
	 * @param request
	 * @param errors
	 * @throws Exception
	 */
	public List<String> parseMultipartRequest(HttpServletRequest request, RegistrationForm formBean, List<FileItem> avatarContainer) throws Exception{
		List<String> errors = new ArrayList<>();

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set factory constraints
		factory.setSizeThreshold(10 * 1024 * 1024);
		factory.setRepository(new File(externalResources));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding(encoding);

		// Set overall request size constraint
		upload.setSizeMax(AVATAR_MAX_SIZE);

		// Parse the request
		List<FileItem> items = upload.parseRequest(request);

		// Process the uploaded items
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
			FileItem item = iter.next();
			// saves values from form
			if (item.isFormField()) {
				switch (item.getFieldName()) {
				case FORM_FIELD_FIRSTNAME: {
					formBean.setFirstName(item.getString(encoding));
					break;
				}
				case FORM_FIELD_LASTNAME: {
					formBean.setLastName(item.getString(encoding));
					break;
				}
				case FORM_FIELD_EMAIL: {
					formBean.setEmail(item.getString(encoding));
					break;
				}
				case FORM_FIELD_PASSWORD: {
					formBean.setPassword(item.getString(encoding));
					break;
				}
				case FORM_FIELD_BIRTHDAY: {
					formBean.setBirthDate(item.getString(encoding));
					break;
				}
				case FORM_FIELD_COMPANY: {
					formBean.setCompany(item.getString(encoding));
					break;
				}
				case FORM_FIELD_ADDRESS1: {
					formBean.setAddress1(item.getString(encoding));
					break;
				}
				case FORM_FIELD_ADDRESS2: {
					formBean.setAddress2(item.getString(encoding));
					break;
				}
				case FORM_FIELD_CITY: {
					formBean.setCity(item.getString(encoding));
					break;
				}
				case FORM_FIELD_POSTCODE: {
					formBean.setPostcode(item.getString(encoding));
					break;
				}
				case FORM_FIELD_ADDITIONAL: {
					formBean.setAdditionalInfo(item.getString(encoding));
					break;
				}
				case FORM_FIELD_PHONE: {
					formBean.setPhone(item.getString(encoding));
					break;
				}
				case FORM_FIELD_CAPTCHA: {
					formBean.setCaptcha(item.getString(encoding));
					break;
				}
				case FORM_FIELD_EXPECTED_CAPTCHA: {
					formBean.setExpectedCaptcha(item.getString(encoding));
					break;
				}
				}
			}
			if(!item.isFormField()){
				log.trace("item ->" + item);
				String itemExtension = ImageUtil.getFileExtension(item);
				if(item.getSize()==0 || ImageUtil.validateImageExtension(itemExtension))
					avatarContainer.add(item);
				else
					errors.add("Check your avatar file");
			}
		}
		return errors;
	}

	/**
	 * Parses simple request
	 * 
	 * @param request
	 * @param formBean
	 * @return empty list of errors
	 * @throws Exception
	 */
	public List<String> parseSimpleRequest(HttpServletRequest request, RegistrationForm formBean) {
		formBean.setFirstName(request.getParameter(FORM_FIELD_FIRSTNAME));
		formBean.setLastName(request.getParameter(FORM_FIELD_LASTNAME));
		formBean.setEmail(request.getParameter(FORM_FIELD_EMAIL));
		formBean.setPassword(request.getParameter(FORM_FIELD_PASSWORD));
		formBean.setBirthDate(request.getParameter(FORM_FIELD_BIRTHDAY));
		formBean.setCompany(request.getParameter(FORM_FIELD_COMPANY));
		formBean.setAddress1(request.getParameter(FORM_FIELD_ADDRESS1));
		formBean.setAddress2(request.getParameter(FORM_FIELD_ADDRESS2));
		formBean.setCity(request.getParameter(FORM_FIELD_CITY));
		formBean.setPostcode(request.getParameter(FORM_FIELD_POSTCODE));
		formBean.setAdditionalInfo(request.getParameter(FORM_FIELD_ADDITIONAL));
		formBean.setPhone(request.getParameter(FORM_FIELD_PHONE));
		formBean.setCaptcha(request.getParameter(FORM_FIELD_CAPTCHA));
		return Collections.emptyList();
	}

	/**
	 * forwards to registration page with determined errors
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	private void forwardBack(HttpServletRequest request, HttpServletResponse response, RegistrationForm formBean, List<String> errors) throws ServletException, IOException{
		if(getServletContext().getInitParameter(Constants.CAPTCHA_MODE).equals(Constants.CAPTCHA_MODE_HIDDEN)){
			String captchaKeyword = captchaService.generateKeyword();
			captchaService.attachCaptcha(captchaKeyword, request, response);
		}
		request.setAttribute(Constants.BEAN_ERRORS, errors);
		request.setAttribute(Constants.FORM_BEAN, formBean);
		if (log.isEnabledFor(Level.DEBUG))
			log.debug("Were found errors in user's info :" + errors);

		request.getRequestDispatcher(response.encodeURL(Constants.PAGE_REGISTRATION)).forward(request, response);
	}
}