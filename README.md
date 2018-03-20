<h1 id="toc_0">一个支持渐变色的圆环进度条控件</h1>

<pre><code>XML支持
1.circleWidth 圆环的宽度
2.circleAngle 圆环的进度
3.backgroundColor 圆环的默认背景颜色
代码支持
1.setCircleWidth 圆环宽度（DP）
2.setColorArray 圆环的渐变颜色数组
3.例如:int[] colorArray = new int[]{Color.parseColor(&quot;#27B197&quot;), Color.parseColor(&quot;#00A6D5&quot;)};
4.如果数组长度为1 那么默认颜色为填充全部无渐变效果
5.setProgress 设置圆环进度（添加由快到慢的插值器）
</code></pre>

