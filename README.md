# LoopScrollImage

forked from https://github.com/mufans/LoopScrollCircleLabel

####summary
#####functional effect：
style similar to  Gallery <br/>

- The widget can show images at infinite loop not matter from left or right side. <br/>
- Screen show max images is 5: SCREEN_SHOW_COUNT=5<br/>
- When images is less than 5 then show images in center screen<br/>
- move images by moveEvent<br/>
- override onTouchEvent()<br/>
- override adjustViewOrder()<br/>
- loop style<br/>
  override looperAdapterWrappergetView() <br/>
  
  the final result:
  
  ![image](https://github.com/SelenaWong/LoopScrollImage/blob/master/app/src/main/res/drawable-hdpi/loopscrollimage.gif)
