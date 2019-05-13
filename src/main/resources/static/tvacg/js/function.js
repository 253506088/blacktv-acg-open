//媒体合作伙伴
$(function() {
$('#hbbtn,#hbcon').mouseover(function() {
	if (window.willhide) clearTimeout(window.willhide);
	$('#hbcon').fadeIn(400)
});
$('#hbbtn,#hbcon').mouseout(function() {
	window.willhide = setTimeout(function() {
		$('#hbcon').fadeOut(400)
	},
	450)
});
});
//选项卡
function shu(x,m,n){

	if(m == undefined)
		m = "";
	else
		m = m + "_";
		
	for(i=1;i<=n;i++){
		if(x==i){
			document.getElementById("table_" + m + i).style.display="block";
			document.getElementById("s_" + m + i).className="sx_" + m + i + "_1";
		}else{
			document.getElementById("table_" + m + i).style.display="none";
			document.getElementById("s_" + m + i).className="sx_" + m + i;
		}
	}
}

//导航下拉菜单
function $$() {
	var elements = new Array();
	for (var i = 0; i < arguments.length; i++) {
		var element = arguments[i];
		if (typeof element == "string") {
			element = document.getElementById(element);
		}
		if (arguments.length == 1) {
			return element;
		}
		elements.push(element);
	}
	return elements;
}
function addLoadEvent(func) {
	var oldonload = window.onload;
	if (typeof window.onload != 'function') {
		window.onload = func;
	} else {
		window.onload = function() {
			oldonload();
			func();
		}
	}
}
var DDSPEED = 6;
var DDTIMER = 15;
function ddMenu(id, d) {
	var h = $$(id + '-ddheader');
	var c = $$(id + '-ddcontent');
	clearInterval(c.timer);
	if (d == 1) {
		clearTimeout(h.timer);
		if (c.maxh && c.maxh <= c.offsetHeight) {
			return
		} else if (!c.maxh) {
			c.style.display = 'block';
			c.style.height = 'auto';
			c.maxh = c.offsetHeight;
			c.style.height = '0px'
		}
		c.timer = setInterval(function() {
			ddSlide(c, 1)
		},
		DDTIMER)
	} else {
		h.timer = setTimeout(function() {
			ddCollapse(c)
		},
		50)
	}
}
function ddCollapse(c) {
	c.timer = setInterval(function() {
		ddSlide(c, -1)
	},
	DDTIMER)
}
function cancelHide(id) {
	var h = $$(id + '-ddheader');
	var c = $$(id + '-ddcontent');
	clearTimeout(h.timer);
	clearInterval(c.timer);
	if (c.offsetHeight < c.maxh) {
		c.timer = setInterval(function() {
			ddSlide(c, 1)
		},
		DDTIMER)
	}
}
function ddSlide(c, d) {
	var currh = c.offsetHeight;
	var dist;
	if (d == 1) {
		dist = (Math.round((c.maxh - currh) / DDSPEED))
	} else {
		dist = (Math.round(currh / DDSPEED))
	}
	if (dist <= 1 && d == 1) {
		dist = 1
	}
	c.style.height = currh + (dist * d) + 'px';
	c.style.opacity = currh / c.maxh;
	c.style.filter = 'alpha(opacity=' + (currh * 100 / c.maxh) + ')';
	if ((currh < 2 && d != 1) || (currh > (c.maxh - 2) && d == 1)) {
		clearInterval(c.timer);
		currh = null;
		dist = null
	}
	if ((d == -1) && (currh <= 4)) {
		clearInterval(c.timer);
		currh = null;
		dist = null
	}
}
 (function() {
	var objNav = $$('nav'),
	arrOneleve = objNav.getElementsByTagName('dt')[0].getElementsByTagName('span'),
	arrTwoleve = objNav.getElementsByTagName('dd')[0].getElementsByTagName('ul'),
	intOneleve = arrOneleve.length,
	objhead = $$('one-ddheader'),
	objcontent = $$('one-ddcontent');
	for (var i = 0; i < intOneleve; i++) {
		arrOneleve[i].onmouseover = arrTwoleve[i].onmouseover = function() {
			for (var j = 0; j < intOneleve; j++) {
				if (arrOneleve[j].className == 'curr' && arrTwoleve[j].className == 'curr') {
					arrOneleve[j].className = '';
					arrTwoleve[j].className = ''
				}
			}
			$$("one" + this.id.substr(3)).className = 'curr';
			$$("two" + this.id.substr(3)).className = 'curr'
		}
	}
	objNav.onmouseout = function() {
		for (var i = 0; i < intOneleve; i++) {
			arrOneleve[i].className = '';
			arrTwoleve[i].className = ''
		}
	};
	objhead.onmouseover = function() {
		ddMenu('one', 1)
	};
	objcontent.onmouseover = function() {
		cancelHide('one')
	};
	objhead.onmouseout = objcontent.onmouseout = function() {
		ddMenu('one', -1)
	}
})();
