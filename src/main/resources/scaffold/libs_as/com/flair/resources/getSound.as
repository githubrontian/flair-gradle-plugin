package com.flair.resources
{
	import _appId_.view.EnumScreen;

	import flash.media.Sound;

	/**
	 *
	 */
	public function getSound( id : String , groupID : String = EnumScreen.MAIN ) : Sound
	{
		return getAssetManager( groupID ).getSound( id );
	}
}