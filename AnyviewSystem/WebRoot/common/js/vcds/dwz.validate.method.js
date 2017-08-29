/**
 * @requires jquery.validate.js
 * @author ZhangHuihua@msn.com
 */
(function($){
	if ($.validator) {
		$.validator.addMethod("alphanumeric", function(value, element) {
			return this.optional(element) || /^\w+$/i.test(value);
		}, "请输入数字，字母和下划线");
		
		$.validator.addMethod("lettersonly", function(value, element) {
			return this.optional(element) || /^[a-z]+$/i.test(value);
		}, "请输入小写字母"); 
		
		$.validator.addMethod("phone", function(value, element) {
			return this.optional(element) || /^[0-9 \(\)]{7,30}$/.test(value);
		}, "请输入7~30位的数字");
		
		$.validator.addMethod("postcode", function(value, element) {
			return this.optional(element) || /^[0-9 A-Za-z]{5,20}$/.test(value);
		}, "请输入5~20位的字母或者数字");
		
		$.validator.addMethod("chinese", function(value, element) {
			return this.optional(element) || /^[\u0391-\uFFE5]{1,10}$/.test(value);
		}, "请输入一到十个汉字"); 
		$.validator.addMethod("ip", function(value, element) {
			return this.optional(element) || /^((?:(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))))$/.test(value);
		}, "请输入正确的ip地址"); 
		
		$.validator.addMethod("date", function(value, element) {
			value = value.replace(/\s+/g, "");
			if (String.prototype.parseDate){
				var $input = $(element);
				var pattern = $input.attr('dateFmt') || 'yyyy-MM-dd';
	
				return !$input.val() || $input.val().parseDate(pattern);
			} else {
				return this.optional(element) || value.match(/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/);
			}
		}, "请输入正确的日期");
		
		$.validator.addMethod("common", function(value, element) {
			return this.optional(element) || /^[0-9a-zA-Z\u4e00-\u9fa5]{3,20}$/.test(value);
		}, "请输入3到20位中文、数字或字母"); 
		
		/*自定义js函数验证
		 * <input type="text" name="xxx" customvalid="xxxFn(element)" title="xxx" />
		 */
		$.validator.addMethod("customvalid", function(value, element, params) {
			try{
				return eval('(' + params + ')');
			}catch(e){
				return false;
			}
		}, "Please fix this field.");
		
		$.validator.addClassRules({
			date: {date: true},
			alphanumeric: { alphanumeric: true },
			lettersonly: { lettersonly: true },
			phone: { phone: true },
			postcode: {postcode: true},
			chinese:{chinese:true},
			ip:{ip:true}
		});
		$.validator.setDefaults({errorElement:"span"});
		$.validator.autoCreateRanges = true;
		
	}

})(jQuery);