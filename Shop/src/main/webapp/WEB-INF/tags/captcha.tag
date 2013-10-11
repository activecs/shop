<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Tag for displaying captcha -->

<div class="control-group">
	<label class="control-label" for="captcha">Captcha <sup>*</sup></label>
	<div class="controls">
		<img alt="captcha" src="<c:url value='/captcha.png?captchaID=${requestScope.captchaID}' />"><br>
	</div>
</div>
<div class="control-group">
	<div class="controls">
		<input type="text" name="captcha" id="captcha"	placeholder="Enter symbols from image" />
	</div>
</div>
<c:if test="${initParam.captchaMode eq 'hiddenField'}">
	<input type="hidden" name="expectedCaptcha"	id="expectedCaptcha" value="${requestScope.captchaID}" />
</c:if>