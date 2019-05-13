//*焦点切换
(function(){
    if(!Function.prototype.bind){
        Function.prototype.bind = function(obj){
            var owner = this,args = Array.prototype.slice.call(arguments),callobj = Array.prototype.shift.call(args);
            return function(e){e=e||top.window.event||window.event;owner.apply(callobj,args.concat([e]));};
        };
    }
})();


//游戏截图
	<!--//--><![CDATA[//><!--
	//图片滚动列表 mengjia 070816
	var Speed = 5; //速度(毫秒)
	var Space = 10; //每次移动(px)
	var PageWidth = 250; //翻页宽度
	var fill = 0; //整体移位
	var MoveLock = false;
	var MoveTimeObj;
	var Comp = 0;
	var AutoPlayObj = null;
	GetObj("List2").innerHTML = GetObj("List1").innerHTML;
	GetObj('ISL_Cont').scrollLeft = fill;
	GetObj("ISL_Cont").onmouseover = function(){clearInterval(AutoPlayObj);}
	GetObj("ISL_Cont").onmouseout = function(){AutoPlay();}
	AutoPlay();
	function GetObj(objName){if(document.getElementById){return eval('document.getElementById("'+objName+'")')}else{return eval
	
	('document.all.'+objName)}}
	function AutoPlay(){ //自动滚动
	clearInterval(AutoPlayObj);
	AutoPlayObj = setInterval('ISL_GoDown();ISL_StopDown();',2000); //间隔时间
	}
	function ISL_GoUp(){ //上翻开始
	if(MoveLock) return;
	clearInterval(AutoPlayObj);
	MoveLock = true;
	MoveTimeObj = setInterval('ISL_ScrUp();',Speed);
	}
	function ISL_StopUp(){ //上翻停止
	clearInterval(MoveTimeObj);
	if(GetObj('ISL_Cont').scrollLeft % PageWidth - fill != 0){
	Comp = fill - (GetObj('ISL_Cont').scrollLeft % PageWidth);
	CompScr();
	}else{
	MoveLock = false;
	}
	AutoPlay();
	}
	function ISL_ScrUp(){ //上翻动作
	if(GetObj('ISL_Cont').scrollLeft <= 0){GetObj('ISL_Cont').scrollLeft = GetObj
	
	('ISL_Cont').scrollLeft + GetObj('List1').offsetWidth}
	GetObj('ISL_Cont').scrollLeft -= Space ;
	}
	function ISL_GoDown(){ //下翻
	clearInterval(MoveTimeObj);
	if(MoveLock) return;
	clearInterval(AutoPlayObj);
	MoveLock = true;
	ISL_ScrDown();
	MoveTimeObj = setInterval('ISL_ScrDown()',Speed);
	}
	function ISL_StopDown(){ //下翻停止
	clearInterval(MoveTimeObj);
	if(GetObj('ISL_Cont').scrollLeft % PageWidth - fill != 0 ){
	Comp = PageWidth - GetObj('ISL_Cont').scrollLeft % PageWidth + fill;
	CompScr();
	}else{
	MoveLock = false;
	}
	AutoPlay();
	}
	function ISL_ScrDown(){ //下翻动作
	if(GetObj('ISL_Cont').scrollLeft >= GetObj('List1').scrollWidth){GetObj('ISL_Cont').scrollLeft =
	
	GetObj('ISL_Cont').scrollLeft - GetObj('List1').scrollWidth;}
	GetObj('ISL_Cont').scrollLeft += Space ;
	}
	function CompScr(){
	var num;
	if(Comp == 0){MoveLock = false;return;}
	if(Comp < 0){ //上翻
	if(Comp < -Space){
	   Comp += Space;
	   num = Space;
	}else{
	   num = -Comp;
	   Comp = 0;
	}
	GetObj('ISL_Cont').scrollLeft -= num;
	setTimeout('CompScr()',Speed);
	}else{ //下翻
	if(Comp > Space){
	   Comp -= Space;
	   num = Space;
	}else{
	   num = Comp;
	   Comp = 0;
	}
	GetObj('ISL_Cont').scrollLeft += num;
	setTimeout('CompScr()',Speed);
	}
	}
	
//媒体专区
function $1(id){return document.getElementById(id)}//获取ID节点的简介方法
var tags=$1("fd").getElementsByTagName("li");//获取切换按钮节点
var cats=$1("fd").getElementsByTagName("p");//获取切换内容节点

var current;//设置当前帧的变量容器
var timer=3000;//	 
function disAll(){//初始所有标签样式
 for(var i=0; i<tags.length; i++){
	   tags[i].className="";
	   cats[i].className="";
	   cats[i].style.display="none";
	 }
  }
function setNow(){//获取当前帧的索引值
  for(var i=0; i<tags.length; i++){
	if(tags[i].className=="now"){
		  current=i;		  
	 }
  } 
}
for(var j=0; j<tags.length; j++){//设置手动切换
	  tags[j].onmouseover=function(){
	   clearInterval(h);	 
		disAll();	  
		 this.className="now";
		  setNow();
		   cats[current].style.display="block";
		   cats[current].className="now";	  
		 }
	  tags[j].onmouseout=function(){
			setNow();	   
			 h=setInterval("goNext()",3000);
		 }
	}
	for(var j=1; j<cats.length; j++){//设置手动切换
	  cats[j].onmouseover=function(){
	   clearInterval(h);	 
		disAll();	  
		 this.className="now";
		  setNow();
		   cats[current].style.display="block";
		   cats[current].className="now";
		   tags[current].className="now";
		 }
	  cats[j].onmouseout=function(){
			setNow();	   
			 h=setInterval("goNext()",3000);
		 }
	}
function goNext(){//自动切换
   setNow();//获取当前帧索引
   current+=1;//帧自增1
	if(current==1){
		document.getElementById("id1").style.marginLeft="1200px";
		document.getElementById("id2").style.marginLeft="0px";
		document.getElementById("id3").style.marginLeft="121px";
		document.getElementById("id4").style.marginLeft="242px";
		document.getElementById("id5").style.marginLeft="363px";
		document.getElementById("id6").style.marginLeft="484px";
		document.getElementById("id7").style.marginLeft="605px";
		document.getElementById("id8").style.marginLeft="726px";
		document.getElementById("id9").style.marginLeft="847px";
		document.getElementById("id10").style.marginLeft="968px";
		document.getElementById("id11").style.marginLeft="1089px";	
	}else if(current==2){
		document.getElementById("id2").style.marginLeft="1200px";
		document.getElementById("id3").style.marginLeft="0px";
		document.getElementById("id4").style.marginLeft="121px";
		document.getElementById("id5").style.marginLeft="242px";
		document.getElementById("id6").style.marginLeft="363px";
		document.getElementById("id7").style.marginLeft="484px";
		document.getElementById("id8").style.marginLeft="605px";
		document.getElementById("id9").style.marginLeft="726px";
		document.getElementById("id10").style.marginLeft="847px";
		document.getElementById("id11").style.marginLeft="968px";
		document.getElementById("id1").style.marginLeft="1089px";
	}else if(current==3){
		document.getElementById("id3").style.marginLeft="1200px";
		document.getElementById("id4").style.marginLeft="0px";
		document.getElementById("id5").style.marginLeft="121px";
		document.getElementById("id6").style.marginLeft="242px";
		document.getElementById("id7").style.marginLeft="363px";
		document.getElementById("id8").style.marginLeft="484px";
		document.getElementById("id9").style.marginLeft="605px";
		document.getElementById("id10").style.marginLeft="726px";
		document.getElementById("id11").style.marginLeft="847px";
		document.getElementById("id1").style.marginLeft="968px";
		document.getElementById("id2").style.marginLeft="1089px";
	}else if(current==4){
		document.getElementById("id4").style.marginLeft="1200px";
		document.getElementById("id5").style.marginLeft="0px";
		document.getElementById("id6").style.marginLeft="121px";
		document.getElementById("id7").style.marginLeft="242px";
		document.getElementById("id8").style.marginLeft="363px";
		document.getElementById("id9").style.marginLeft="484px";
		document.getElementById("id10").style.marginLeft="605px";
		document.getElementById("id11").style.marginLeft="726px";
		document.getElementById("id1").style.marginLeft="847px";
		document.getElementById("id2").style.marginLeft="968px";
		document.getElementById("id3").style.marginLeft="1089px";	
	}else if(current==5){
		document.getElementById("id5").style.marginLeft="1200px";
		document.getElementById("id6").style.marginLeft="0px";
		document.getElementById("id7").style.marginLeft="121px";
		document.getElementById("id8").style.marginLeft="242px";
		document.getElementById("id9").style.marginLeft="363px";
		document.getElementById("id10").style.marginLeft="484px";
		document.getElementById("id11").style.marginLeft="605px";
		document.getElementById("id1").style.marginLeft="726px";
		document.getElementById("id2").style.marginLeft="847px";
		document.getElementById("id3").style.marginLeft="968px";
		document.getElementById("id4").style.marginLeft="1089px";
	}else if(current==6){
		document.getElementById("id6").style.marginLeft="1200px";
		document.getElementById("id7").style.marginLeft="0px";
		document.getElementById("id8").style.marginLeft="121px";
		document.getElementById("id9").style.marginLeft="242px";
		document.getElementById("id10").style.marginLeft="363px";
		document.getElementById("id11").style.marginLeft="484px";
		document.getElementById("id1").style.marginLeft="605px";
		document.getElementById("id2").style.marginLeft="726px";
		document.getElementById("id3").style.marginLeft="847px";
		document.getElementById("id4").style.marginLeft="968px";
		document.getElementById("id5").style.marginLeft="1089px";	
	}
	else if(current==7){
		document.getElementById("id7").style.marginLeft="1200px";
		document.getElementById("id8").style.marginLeft="0px";
		document.getElementById("id9").style.marginLeft="121px";
		document.getElementById("id10").style.marginLeft="242px";
		document.getElementById("id11").style.marginLeft="363px";
		document.getElementById("id1").style.marginLeft="484px";
		document.getElementById("id2").style.marginLeft="605px";
		document.getElementById("id3").style.marginLeft="726px";
		document.getElementById("id4").style.marginLeft="847px";
		document.getElementById("id5").style.marginLeft="968px";
		document.getElementById("id6").style.marginLeft="1089px";
	}else if(current==8){
		document.getElementById("id8").style.marginLeft="1200px";
		document.getElementById("id9").style.marginLeft="0px";
		document.getElementById("id10").style.marginLeft="121px";
		document.getElementById("id11").style.marginLeft="242px";
		document.getElementById("id1").style.marginLeft="363px";
		document.getElementById("id2").style.marginLeft="484px";
		document.getElementById("id3").style.marginLeft="605px";
		document.getElementById("id4").style.marginLeft="726px";
		document.getElementById("id5").style.marginLeft="847px";
		document.getElementById("id6").style.marginLeft="968px";
		document.getElementById("id7").style.marginLeft="1089px";
	}else if(current==9){
		document.getElementById("id9").style.marginLeft="1200px";
		document.getElementById("id10").style.marginLeft="0px";
		document.getElementById("id11").style.marginLeft="121px";
		document.getElementById("id1").style.marginLeft="242px";
		document.getElementById("id2").style.marginLeft="363px";
		document.getElementById("id3").style.marginLeft="484px";
		document.getElementById("id4").style.marginLeft="605px";
		document.getElementById("id5").style.marginLeft="726px";
		document.getElementById("id6").style.marginLeft="847px";
		document.getElementById("id7").style.marginLeft="968px";
		document.getElementById("id8").style.marginLeft="1089px";	
	}
	else if(current==10){
		document.getElementById("id10").style.marginLeft="1200px";
		document.getElementById("id11").style.marginLeft="0px";
		document.getElementById("id1").style.marginLeft="121px";
		document.getElementById("id2").style.marginLeft="242px";
		document.getElementById("id3").style.marginLeft="363px";
		document.getElementById("id4").style.marginLeft="484px";
		document.getElementById("id5").style.marginLeft="605px";
		document.getElementById("id6").style.marginLeft="726px";
		document.getElementById("id7").style.marginLeft="847px";
		document.getElementById("id8").style.marginLeft="968px";
		document.getElementById("id9").style.marginLeft="1089px";	
	}
	else if(current==11){
		document.getElementById("id11").style.marginLeft="1200px";
		document.getElementById("id1").style.marginLeft="0px";
		document.getElementById("id2").style.marginLeft="121px";
		document.getElementById("id3").style.marginLeft="242px";
		document.getElementById("id4").style.marginLeft="363px";
		document.getElementById("id5").style.marginLeft="484px";
		document.getElementById("id6").style.marginLeft="605px";
		document.getElementById("id7").style.marginLeft="726px";
		document.getElementById("id8").style.marginLeft="847px";
		document.getElementById("id9").style.marginLeft="968px";
		document.getElementById("id10").style.marginLeft="1089px";
	}
  if(current>=parseInt(tags.length)){//判断：如果当前帧索引值是否大于切换按钮总数，如果大于按钮总数则回到初始状态
	current=0;
	  disAll();
	   cats[0].style.display="block";
		tags[0].className="now";
		 cats[0].className="now";
  }else{
	   disAll();
	   cats[current].style.display="block";
	   cats[current].className="now";
	   tags[current].className="now";
		
   }
}
var h=setInterval("goNext()",timer)//开始自动切换
function getCookie(name)//取cookies函数          
{  
	var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));  
	 if(arr != null) return unescape(arr[2]); return null;  
}