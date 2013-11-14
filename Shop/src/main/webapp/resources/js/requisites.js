$(window).load(function() {
	$('#requisites-form').on('submit', function(event) {
		if (!requisites.checkForm()) {
			return false;
		}
	});
});

var requisites = {
		checkForm : function(){
			$('#errors').html('');
			var hasError = false;
			if (!this.validateCity($('#requisites-form #city').val())) {
				this.appendError('Check your city');
				hasError = true;
			}
			if ($('#requisites-form #address').val().length < 3 || $('#requisites-form #address').val().length > 30) {
				this.appendError('Check your address (minimal length 3 symbols)');
				hasError = true;
			}
			if (!this.validatePhone($('#requisites-form #phone').val())) {
				this.appendError('Check your phone number (minimal length 5 symbols)');
				hasError = true;
			}
			if (hasError) {
				this.scrollToTop();
				return false;
			} else {
				return true;
			}
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
	
		// scroll to top
		scrollToTop : function() {
			$('html, body').animate({
				scrollTop : $("#errors").position().top
			}, 800);
		},
		
		// checks city
		validateCity : function(city) {
			var re = /([А-я]|[A-z]){2,20}([- ]([А-я]|[A-z]){2,20}|)/;
			var result = re.test(city); 
			return result;
		},
		
		// checks phone
		validatePhone : function(phone){
			var re = /[\+|\d][\d -]{4,17}/;
			return re.test(phone);
		}
};