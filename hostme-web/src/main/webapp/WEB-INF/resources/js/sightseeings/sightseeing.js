/**
 * Script for feedbacks
 */
$(document).ready(

		function() {

			$('#chat-box').slimScroll({
				height : window.screen.availHeight / 4,
			});

			$('#userMsg').keypress(function(e) {
				if (e.which == 13) {
					$("#sendMsg").click();
					return false;
				}
			});

			$(function(i) {
				$('.demo-8').oka_slider_model({
					'type' : 7,
					'animateSpeed' : 300,
					'speed' : 5000
				});
			});
			
			 $(".triggerRemove").click(
				        function(e) {
				            e.preventDefault();
				            $("#modalRemove .removeBtn").attr("href",
				                $(this).attr("href"));
				            $("#modalRemove").modal();
				        });
			$(function() {
				function validateDonotSelect(value, element, param) {
					if (value == param) {
						return false;
					} else {
						return true;
					}
				}
				jQuery.validator.addMethod("do_not_select",
						validateDonotSelect, "Please select an option");
				$('.groupForm').validate(
						{
							rules : {
								name : {
									required : true,
									minlength : 3,
									maxlength : 42
								},
								description : {
									required : true,
									minlength : 5,
									maxlength : 255
								},
								address : {
									required : true,
									minlength : 3,
									maxlength : 42
								},
								"sightseeingType" : {
									do_not_select : '0'
								},
								"priceCategory.priceCategory" : {
									do_not_select : '0'
								},
								"city.country.country" : {
									do_not_select : '0'
								},
								"city.city" : {
									do_not_select : '0'
								}
							},
							messages : {
								"sightseeingType" : {
									do_not_select : "Please select a type"
								},
								"priceCategory.priceCategory" : {
									do_not_select : "Please select a category"
								},
								"city.country.country" : {
									do_not_select : "Please select a country"
								},
								"city.city" : {
									do_not_select : "Please select a city"
								}
							},
							highlight : function(element) {
								$(element).closest('.form-group').removeClass(
										'has-success').addClass('has-error');
							},
							unhighlight : function(element) {
								$(element).closest('.form-group').removeClass(
										'has-error').addClass('has-success');
							}
						});
			});
		});

function loadPostsAjax(placeId) {

	$.ajax({
		url : "findComments.json",
		type : 'GET',
		data : {
			placeId : placeId
		},
		dataType : "json",
		beforeSend : function() {
			$("#chat-box").html(loader);
		},
		success : function(result) {
			if (result.length > 0) {
				showPosts(result);
			} else {
				$("#chat-box").html("Here are no posts, yet.");
			}
		}
	});
}

function showPosts(posts) {
	$("#chat-box").html("");
	for (var i = 0; i < posts.length; i++) {
		createChatItem(posts[i]);
	}
	var scrollTo_int = $('#chat-box').prop('scrollHeight') + 'px';
	$('#chat-box').slimScroll({
		scrollTo : scrollTo_int
	});
}

function createChatItem(postDto) {

	var item = document.createElement("DIV");
	item.className = "item";
	var image = document.createElement("IMG");
	image.src = postDto.imageUrl;
	image.className = "online";

	var message = document.createElement("P");
	message.className = "message";
	var name = document.createElement("A");
	name.className = "name";
	name.href = "hoster?hosterId=" + postDto.authorId;

	var small = document.createElement("SMALL");
	small.className = "'text-muted pull-right";
	var i = document.createElement("I");
	i.className = "fa fa-clock-o";
	var time = document.createTextNode(postDto.postTime);
	small.appendChild(i);
	small.appendChild(time);

	var tname = document.createTextNode(postDto.author);
	name.appendChild(small);
	name.appendChild(tname);
	message.appendChild(name);

	var t = document.createTextNode(postDto.content);
	message.appendChild(t);

	item.appendChild(image);
	item.appendChild(message);

	var chat = document.getElementById("chat-box");

	chat.appendChild(item);
}

function sendMessage(placeId, message) {
	$("#userMsg").val("");

	$.ajax({
		url : "sendComment",
		type : 'GET',
		data : {
			placeId : placeId,
			message : message
		},
		dataType : "json",
		beforeSend : function() {
			$("#chat-box").html(loader);
		},
		success : function(result) {
			if (result.length > 0) {
				showPosts(result);
			} else {
				$("#chat-box").html("Failed to load posts");
			}
		}
	});

}

var loader = $(
		"<img/>",
		{
			src : "resources/images/ajax-loader-128px.gif",
			style : "width:128px;  display: block; margin-left: auto; margin-right: auto;"
		});

jQuery()
		.ready(
				function() {
					var priceCategories = [];
					$
							.getJSON(
									'getPriceCategories',
									function(data) {
										$.each(data, function(index, val) {
											priceCategories[index] = val;
										});
										var $priceOption = '<option value="0">Select price category</option>';
										for (var i = 0; i < priceCategories.length; i++) {
											var price = priceCategories[i].priceCategory;
											$priceOption += '<option value="'
													+ price + '">' + price
													+ '</option>';
										}
										;
										$('#price').html($priceOption);
									});
				});
jQuery()
		.ready(
				function() {
					var types = [];
					$
							.getJSON(
									'getAllTypes',
									function(data) {
										$.each(data, function(index, val) {
											types[index] = val;
										});
										var $typeOption = '<option value="0">Select sightseeing type</option>';
										for (var i = 0; i < types.length; i++) {
											var type = types[i];
											$typeOption += '<option value="'
													+ type + '">' + type
													+ '</option>';
										}
										;
										$('#sstype').html($typeOption);
									});
				});
jQuery()
		.ready(
				function() {
					var tabCountry = [];
					$.getJSON('getAllCountries', function(data) {
						$.each(data, function(index, val) {
							tabCountry[index] = val;
						});
						$country = $(this).val()
						var $countryOption = '<option value="' + $country
								+ '">' + $country + '</option>';
						for (var i = 0; i < tabCountry.length; i++) {
							var country = tabCountry[i].country;
							$countryOption += '<option value="' + country
									+ '">' + country + '</option>';
						}
						;
						$('#country').html($countryOption);
					});
					$('#country')
							.change(
									function(event) {
										$country = $(this).val();
										$city = $(this).val();
										var $htmlOption;
										for (var i = 0; i < tabCountry.length; i++) {
											if ($country === tabCountry[i].country) {
												for (var j = 0; j < tabCountry[i].city.length; j++) {
													var town = tabCountry[i].city[j].city;
													$htmlOption += '<option value="'
															+ town
															+ '">'
															+ town
															+ '</option>';
												}
											}
										}
										$('#city').html($htmlOption);
									});
				});
