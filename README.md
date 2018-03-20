# CircleProgress
一个支持渐变色的圆环进度条控件</br>
XML支持 </br>
1.circleWidth 圆环的宽度 </br>
2.circleAngle 圆环的进度 </br>
3.backgroundColor 圆环的默认背景颜色</br>
代码支持 </br>
1.setCircleWidth 圆环宽度（DP） </br>
2.setColorArray 圆环的渐变颜色数组 </br>
3.例如:int[] colorArray = new int[]{Color.parseColor("#27B197"), Color.parseColor("#00A6D5")};</br>
4.如果数组长度为1 那么默认颜色为填充全部无渐变效果</br>
5.setProgress 设置圆环进度（添加由快到慢的插值器）</br>

