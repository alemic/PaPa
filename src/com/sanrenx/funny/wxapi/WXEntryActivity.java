/******************************************************************************
 * 
 * Copyright (c) 2012 ~ 2020 Baidu.com, Inc. All Rights Reserved
 * 
 *****************************************************************************/
package com.sanrenx.funny.wxapi;

import com.baidu.frontia.activity.share.FrontiaWeixinShareActivity;

/**
 * @author zhangchi09
 *
 */
public class WXEntryActivity extends FrontiaWeixinShareActivity {
	
	@Override
	protected boolean handleIntent() {
		if (super.handleIntent()) {
			return true;
		} else {
			return false;
		}
	}
}
