<!--
 !Vsevolod Geraskin
 !Final Project
-->
<%@include file="common/taglibs.jsp"%>

 <html>
    <head>
      <title>Vsevolod Geraskin - COMP 8061 Final Project</title>
      <link rel="stylesheet" type="text/css" href="/final/css/style.css">
      <!--[if lte IE 6]><link rel="stylesheet" href="style_ie.css" type="text/css" media="screen, projection" /><![endif]-->
      <script type="text/javascript" src="/final/js/prototype.js"></script>
          <script type="text/javascript">
      		var Updater,ListUpdater;
      		
      		function startUpdater() {
      			Updater = new Ajax.PeriodicalUpdater('notify', 'Notify.action',
	        	          { method: 'post',
	        	            frequency: 1, 
	        	            decay: 1
	        	          }
	        	        );	
      			
      			ListUpdater = new Ajax.PeriodicalUpdater('notifylist', 'NotifyList.action',
	        	          { method: 'post',
	        	            frequency: 3, 
	        	            decay: 1
	        	          }
	        	        );
      		}
      		
      		function stopUpdater() {
      			Updater.stop();
      			ListUpdater.stop();
      		}
      		
      		function doconnect(control) { 
      			

      	        var form = control.form;
      	        
		        	new Ajax.Updater('result', form.action,
		        	          { method: 'post',
		        	            parameters: $('main').serialize({submit: control.name})
		        	          }
		        	);
      	       	
      	      	form['connect'].disabled=true;
	        	form['disconnect'].disabled=false;
	        	form['broadcast'].disabled=false;     
	        	form['chatLine'].disabled=false;
	        	
	        	form['chatLine'].focus();
	        	
      	        return false;
      	      }
      		
      		function dodisconnect(control) {
      			var form = control.form;
           	 	
	           	 	new Ajax.Updater('result', form.action,
	       	          	{ method: 'post',
	       	            	parameters: $('main').serialize({submit: control.name})
	       	          	}
	       			);
      			
      			form['connect'].disabled=false;
           	  	form['disconnect'].disabled=true;
           	  	form['broadcast'].disabled=true;     
           	  	form['chatLine'].disabled=true;

		        return false;
      		}
      		
      		function dobroadcast(control) {
      			var form = control.form;
      			
	      			new Ajax.Updater('result', form.action,
	           	          	{ method: 'post',
	           	            	parameters: $('main').serialize({submit: control.name})
	           	          	}
	           			);
      			
      			form['chatLine'].value ="";
      			form['chatLine'].focus();
      			
      			return false;
      		}
    </script>
    </head>
    <body onload="startUpdater();" onunload="stopUpdater();">
    <div id="wrapper">

	<div id="header">
	<div id="containertop">
    <s:form id="main" beanclass="action.ChatActionBean">
   <table>
   		<tr>
          <td>Welcome, <strong>${fn:escapeXml(actionBean.username)}</strong>! 
          <s:link beanclass="action.LogoutActionBean" event="logout">[ logout ]</s:link>
          </td>
          <td>
          <c:if test="${actionBean.connected}"> 
          <s:submit name="connect" disabled ="true" value="Connect" onclick="return doconnect(this);"/>
          <s:submit id="disconnect" name="disconnect" value="Disconnect" onclick="return dodisconnect(this);"/>
          </c:if>
          <c:if test="${!actionBean.connected}"> 
          <s:submit name="connect" value="Connect" onclick="return doconnect(this);"/>
          <s:submit id="disconnect" name="disconnect" disabled ="true" value="Disconnect" onclick="return dodisconnect(this);"/>
          </c:if>
          </td>
          <c:if test="${actionBean.chatto == null}"> 
          <td>say to everyone:</td>
          </c:if>
          <c:if test="${actionBean.chatto != null}"> 
          <td>
           <s:link beanclass="action.ChatActionBean"
          event="cancel">
          [ stop whispering ]
        </s:link>
          whisper to ${fn:escapeXml(actionBean.chatto)}:</td>
          </c:if>
          <c:if test="${actionBean.connected}"> 
          <td><s:text name="chatLine"/></td>
          <td><s:submit name="broadcast" value="Chat" onclick="return dobroadcast(this);"/></td>
          </c:if>
          <c:if test="${!actionBean.connected}"> 
          <td><s:text disabled ="true" name="chatLine"/></td>
          <td><s:submit name="broadcast" disabled ="true" value="Chat" onclick="return dobroadcast(this);"/></td>
          </c:if>
          <td><div id="result"></div></td>
        </tr>
        
	</table>
    </s:form>
    </div>
    </div>
    <div id="middle">
    <div id="containerbeige">
    <div id="notify"></div>
    </div>
    <div class="sidebar" id="sideRight"><div id="containergray"><div id="notifylist"></div></div></div>
    </div>
    </div>
    <script type="text/javascript">
    document.observe('keydown', function(event){
        if(event.keyCode == Event.KEY_RETURN) {
            document.forms[0]['broadcast'].click();
            // stop processing the event
            Event.stop(event);
        }
    });
    </script>
    </body>
  </html>