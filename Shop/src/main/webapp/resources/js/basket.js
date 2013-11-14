var basket = {
	
	URL : 'basket',
	
	updateFields : function(data, productId){
		$(".basketQuantity").html(data.basketQuantity);
		$("input#count"+productId).attr("placeholder",data.quantity);
		$("td#total"+productId).html('$'+(data.price*data.quantity).toFixed(2));
		$(".basketCost").html('$'+(+data.basketCost).toFixed(2));
	},
	
	addToBasket : function(productId) {
		$.ajax({
			type : 'POST',
			cashe : false,
			data: "productId="+productId+"&action=add",
			dataType : 'json',
			url : this.URL,
			success : function(data) {
				basket.updateFields(data, productId);
			}
		});
	},
	
	reduceInBasket : function(productId) {
		$.ajax({
			type : 'POST',
			cashe : false,
			data: "productId="+productId+"&action=reduce",
			dataType : 'json',
			url : this.URL,
			success : function(data) {
				if(data.quantity <= 0)
					window.location.reload();				
				basket.updateFields(data, productId);
			}
		});
	},
	
	removeFromBasket : function(productId) {
		$.ajax({
			type : 'POST',
			cashe : false,
			data: "productId="+productId+"&action=remove",
			dataType : 'json',
			url : this.URL,
			success : function(data) {
				window.location.reload(); 
			}
		});
	}
};