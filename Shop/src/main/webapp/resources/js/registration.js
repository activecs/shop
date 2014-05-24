$(window).load(function() {
	
	// write expected captcha to hidden field
	//captcha.writeToHiddenField();
	
	$('#registration-form').on('submit', function(event) {
		if (!registration.checkForm()) {
			return false;
		}

	});
	// activate bootsrap datepicker
	$(function() {
		$('.input-append.date').datepicker({
			format : "dd-mm-yyyy",
			startDate : "01/01/1900",
			endDate : "01/01/2010",
			startView : 2,
			language : "ru",
			orientation : "bottom auto",
			autoclose : true,
			todayHighlight : true
		});
	});
	
	 // send ajax query
    $('#registration-form #inputEmail').bind("change keyup blur", function() {    		
    	if(registration.validateEmail($(this).val())){        	
    			ajaxJson.sendRequest();        		
        }
    });
	
});

var registration = {
	// whether all fields are not empty
	checkForm : function() {
		$('#errors').html('');
		var hasError = false;
		if (!this.validateName($('#registration-form #firstName').val())) {
			this.appendError('Check your first name (minimal length 2 symbols, maximal 15)');
			hasError = true;
		}
		if (!this.validateName($('#registration-form #lastName').val())) {
			this.appendError('Check your last name (minimal length 2 symbols, maximal 15)');
			hasError = true;
		}
		if (!this.validateEmail($('#registration-form #inputEmail').val())) {
			this.appendError('Check your email');
			hasError = true;
		}
		if ($('#registration-form #password').val().length < 6) {
			this.appendError('Check your password (minimal length 6 symbols)');
			hasError = true;
		}
		if ($('#registration-form #address1').val().length < 3
				|| $('#registration-form #address2').val().length < 3) {
			this.appendError('Check your address (minimal length 3 symbols)');
			hasError = true;
		}
		if (!this.validateCity($('#registration-form #city').val())) {
			this.appendError('Check your city');
			hasError = true;
		}
		if (!this.validatePostcode($('#registration-form #postcode').val())) {
			this.appendError('Check your postcode (minimal length 4 symbols)');
			hasError = true;
		}
		if (!this.validatePhone($('#registration-form #phone').val())) {
			this.appendError('Check your phone number (minimal length 5 symbols)');
			hasError = true;
		}
		if ($('#registration-form #captcha').val() == '') {
			this.appendError('Check captcha');
			hasError = true;
		}

		if (hasError) {
			this.scrollToTop();
			return false;
		} else {
			return true;
		}
	},
	// checks name
	validateName : function(name){
		var re = /\S{2,15}/;
		return re.test(name);
	},
	// checks phone
	validatePhone : function(phone){
		var re = /[\+|\d][\d -]{4,17}/;
		return re.test(phone);
	},
	// checks postcode
	validatePostcode : function(code){
		var re = /\d{4,10}/;
		return re.test(code);
	},
	// checks city
	validateCity : function(city) {
		var re = /([А-я]|[A-z]){2,20}([- ]([А-я]|[A-z]){2,20}|)/;
		return re.test(city);
	},
	// checks email
	validateEmail : function(email) {
		var re = /\S+@\S+\.\S+/;
		return re.test(email);
	},
	// shows error
	appendError : function(message) {
		$('#errors')
				.append(
						"<div class='alert alert-info fade in'> \
				<button type='button' class='close' data-dismiss='alert'>×</button> \
				<strong>"
								+ message + "</strong> \
			</div>");
	},
	// removes all errors
	removeErrors : function() {
		$('#errors').empty();
	},
	// scroll to top
	scrollToTop : function() {
		$('html, body').animate({
			scrollTop : 0
		}, 800);
	},
	// checks whether n is numeric
	isNumeric : function(n) {
		return !isNaN(parseFloat(n)) && isFinite(n);
	}
};

var captcha = {
		cookieName : 'expectedCaptcha',
		
		writeToHiddenField : function(){
			$('#expectedCaptcha').val($.cookie(this.cookieName));
		}
};

var ajaxJson = {
	
	URL : 'asyncRegistration',
	
	sendRequest : function() {
		$.ajax({
			type : 'GET',
			cashe : false,
			data: "email="+$('#registration-form #inputEmail').val(),
			dataType : 'json',
			url : ajaxJson.URL,
			success : function(data) {
				
				registration.removeErrors();
				$("#emailControlGroup").removeClass("error");
				
				if (data.code != 0) {
					$("#emailControlGroup").addClass("error");
					registration.appendError(data.message);
				}
			}
		});
	}
};