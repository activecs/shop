//shop-----------------------//
$(document).ready(function(){
	/* carousel of home page animation */
	$('#myCarousel').carousel({
	  interval: 7000
	})
	 $('#featured').carousel({
	  interval: 7000
	})
	$(function() {
		$('#gallery .item a').lightBox();
	});
	
	/* sets a large thumbnail after page loading */
	$(function() {
		var thumbnail = $('.item.active > a').first();
		var href = $(thumbnail).attr('href');
		$('#gallery > a[title="zoom"] img').attr('src', href);
	});
	
	// if you click on big thumbnail will open
	// photo gallery
	$('#gallery > a[title="zoom"]').on('click', function(event){
		var href = $(this).find('img').attr('src');
		$('.item.active > a[href="'+ href +'"]').click();
	});
	
	$('.subMenu > a').click(function(e)
	{
		e.preventDefault();
		var subMenu = $(this).siblings('ul');
		var li = $(this).parents('li');
		var subMenus = $('#sidebar li.subMenu ul');
		var subMenus_parents = $('#sidebar li.subMenu');
		if(li.hasClass('open'))
		{
			if(($(window).width() > 768) || ($(window).width() < 479)) {
				subMenu.slideUp();
			} else {
				subMenu.fadeOut(250);
			}
			li.removeClass('open');
		} else 
		{
			if(($(window).width() > 768) || ($(window).width() < 479)) {
				subMenus.slideUp();			
				subMenu.slideDown();
			} else {
				subMenus.fadeOut(250);			
				subMenu.fadeIn(250);
			}
			subMenus_parents.removeClass('open');		
			li.addClass('open');	
		}
	});
	var ul = $('#sidebar > ul');
	$('#sidebar > a').click(function(e)
	{
		e.preventDefault();
		var sidebar = $('#sidebar');
		if(sidebar.hasClass('open'))
		{
			sidebar.removeClass('open');
			ul.slideUp(250);
		} else 
		{
			sidebar.addClass('open');
			ul.slideDown(250);
		}
	});

});
