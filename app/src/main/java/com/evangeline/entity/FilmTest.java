package com.evangeline.entity;

import java.util.ArrayList;
import java.util.List;


public class FilmTest {

	public static String[] urls = new String[] {
			"http://img.cxdq.com/d1/img/070609/20076962820825.jpg",
			"http://www.ocn.com.cn/Upload/userfiles/25(128).jpg",
			"http://image11.m1905.cn/uploadfile/2012/1025/20121025115127717.jpg",
			"http://www.sndu.com.cn/resource/images/201511/20151125132158625.jpeg",
			"http://img31.mtime.cn/mg/2014/09/30/145438.41392832_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/09/09/095439.24895990_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/10/13/151034.85474901_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/09/23/084444.96794628_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/08/15/104026.33444853_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/09/26/151251.44963343_270X405X4.jpg"
	};

	public static String[] names = new String[] { 
			"哈利波特", "饥饿游戏", "暮光",
			"剑雨", "3D食人鱼", "心花怒放", "忍者神龟","移动迷宫","魁拔","史来贺"
			};

	
	public static List<Film> getProducts() {
		List<Film> proList = new ArrayList<Film>();
		for (int i = 0; i < 5; i++) {
			int price = (i+1)*500;
			Film film = new Film(i+1,names[i], urls[i],price,20);//价钱单位分
			proList.add(film);
		}
		return proList;
	}

}
