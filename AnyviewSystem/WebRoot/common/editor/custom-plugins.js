/**Browse the remote server, select the file and open it*/
KE.lang.open = '\u6253\u5f00\u5b66\u4e60\u5185\u5bb9';
KE.plugin.open = {
	click : function(id) {
		KE.util.selection(id);
		this.dialog = new KE.dialog( {
			id : id,
			cmd : 'file_manager',
			file : 'file_manager/file_manager.html?oper=sc@open',
			width : 500,
			height : 420,
			loadingMode : true,
			title : KE.lang.fileManager + '--\u6253\u5f00'
		});
		this.dialog.show();
	}
};
/**Browse the remote server, save the file to the server*/
KE.lang.save = '\u4fdd\u5b58\u5b66\u4e60\u5185\u5bb9';
KE.plugin.save = {
	click : function(id) {
		KE.util.selection(id);
		this.dialog = new KE.dialog( {
			id : id,
			cmd : 'file_manager',
			file : 'file_manager/file_manager.html?oper=sc@save',
			width : 500,
			height : 400,
			loadingMode : true,
			title : KE.lang.fileManager + '--\u4fdd\u5b58'
		});
		this.dialog.show();
	}
};
/**Open the template*/
KE.lang.template = '\u63d2\u5165\u6a21\u677f';
KE.plugin.template = {
	click : function(id) {
		KE.util.selection(id);
		this.dialog = new KE.dialog( {
			id : id,
			cmd : 'template',
			file : 'template/template.html',
			width : 500,
			height : 400,
			loadingMode : true,
			title : '\u63d2\u5165\u6a21\u677f',
			yesButton : KE.lang['yes']
		});
		this.dialog.show();
	}
};
/**Preview the editor*/
KE.lang.preview = '\u9884\u89c8';
KE.plugin.preview = {
	click : function(id) {
		KE.util.selection(id);
		this.dialog = new KE.dialog( {
			id : id,
			cmd : 'preview',
			file : 'preview/preview.html',
			width : 19*KE.util.getDocumentWidth(document)/20,
			height : 4*KE.util.getDocumentHeight(document)/5,
			loadingMode : true,
			title : '\u9884\u89c8',
			noButton : KE.lang['no']
		});
		this.dialog.show();
	}
};
/**Clear the contents*/
KE.lang.clear = '\u6e05\u7a7a\u5185\u5bb9';
KE.plugin.clear = {
	click : function(id) {
	    KE.html(id,"");
	}
};
/**Insert code to the editor*/
KE.lang.code = '\u63d2\u5165\u4ee3\u7801';
KE.plugin.code = {
	click : function(id) {
		KE.util.selection(id);
		this.dialog = new KE.dialog( {
			id : id,
			cmd : 'code',
			file : 'code/code.html',
			width : 9*KE.util.getDocumentWidth(document)/10,
			height : 2 * KE.util.getDocumentHeight(document) / 3,
			loadingMode : true,
			title : '\u63d2\u5165\u4ee3\u7801',
			yesButton : KE.lang['yes']
		});
		this.dialog.show();
	}
};
/**Restore the old version*/
KE.lang.version = '\u7248\u672c\u6062\u590d';
KE.plugin.version = {
	click : function(id) {
		KE.util.selection(id);
		this.dialog = new KE.dialog( {
			id : id,
			cmd : 'version_manager',
			file : 'version_manager/version_manager.html',
			width : 500,
			height : 400,
			loadingMode : true,
			title : '\u7248\u672c\u6062\u590d'
		});
		this.dialog.show();
	}
};