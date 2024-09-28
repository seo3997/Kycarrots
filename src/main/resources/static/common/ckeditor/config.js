/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'en';
	// config.uiColor = '#AADC6E';

	config.height = '40vh';
	
	config.font_names = 'Source Sans Pro' + CKEDITOR.config.font_names;
	config.language = "ko";
    config.enterMode = CKEDITOR.ENTER_BR;           // 에디터상에서 엔터입력시 <br />로 적용
    config.startupFocus = true;                                     // 시작시 포커스 설정
    
    // Make dialogs simpler.
	config.removeDialogTabs = 'image:advanced;link:advanced';
	
	config.toolbar =[
		['Source','-','Cut','Copy','Paste','PasteText','PasteFromWord','Undo','Redo','SelectAll','RemoveFormat'],
		['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
		['Image','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
		['NumberedList','BulletedList','Outdent','Indent','Blockquote','CreateDiv'],
		'/',
		['Bold','Italic','Underline','Strike', 'Subscript','Superscript'],
		['Styles','Format','Font','FontSize','TextColor','BGColor','Maximize', 'ShowBlocks']
	];
};
