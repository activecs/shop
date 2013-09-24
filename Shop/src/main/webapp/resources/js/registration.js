$(document).ready(function(){
	$('.btn[type="button"]').on('click', function(event){
		registration.checkForm();
		
		return false;
	});
});

var registration = {
	// checks email
	 validateEmail : function(){
		var email = $('#input_email').val();
		var re = /\S+@\S+\.\S+/;
        return re.test(email);
	 },
	 // checks date
	 validateDate : function(){
		var day = $('select[name="day"] :selected').val();
		var month = $('select[name="month"] :selected').val();
		var year = $('select[name="year"] :selected').val();
		var date = new Date(year, month, day);
		if (year == date.getFullYear() && month == date.getMonth() && day == date.getDate()){ 
			return true;
		}
		return false;
	 },
	 // whether all fields are not empty
	checkForm : function(){
		$('#errors').html('');
		var hasError = false;
		if($('.form-horizontal #inputFname').val() == ''){			
			this.appendError('Check your first name');
			hasError = true;
		};
		if($('.form-horizontal #inputLnam').val() == ''){
			this.appendError('Check your last name');
			hasError = true;
		};
		if(!this.validateEmail($('.form-horizontal #input_email').val())){
			this.appendError('Check your email');
			hasError = true;
		};
		if($('.form-horizontal #inputPassword1').val() == ''){
			this.appendError('Check your password');
			hasError = true;
		};
		if(!this.validateDate()){
			this.appendError('Check your birth date');
			hasError = true;
		};
		if($('.form-horizontal #address1').val() == '' || $('.form-horizontal #address2').val() == ''){
			this.appendError('Check your address');
			hasError = true;
		};
		if($('.form-horizontal #city').val() == ''){
			this.appendError('Check your city');
			hasError = true;
		};
		if($('.form-horizontal #postcode').val() == ''){
			this.appendError('Check your postcode');
			hasError = true;
		};
		if($('.form-horizontal #phone').val() == ''){
			this.appendError('Check your phone number');
			hasError = true;
		};
		
		if(hasError){
			this.scrollToTop();
			return false;
		}else{
			return true;
		}
	},
	// shows error
	appendError : function(message){
		$('#errors').append(
			"<div class='alert alert-info fade in'> \
				<button type='button' class='close' data-dismiss='alert'>×</button> \
				<strong>"+ message +"</strong> \
			</div>");
	},
	// scroll to top
	scrollToTop : function(){ 
		$("html, body").animate({ scrollTop: 0 }, 800);
	}
};