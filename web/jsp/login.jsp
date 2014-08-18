<!--
 !Vsevolod Geraskin
 !Final Project
-->
<%@include file="common/taglibs.jsp"%>

 <html>
    <head>
      <title>Vsevolod Geraskin - COMP 8061 Final Project</title>
      <link rel="stylesheet" type="text/css"
        href="/final/css/style.css">
        <!--[if lte IE 6]><link rel="stylesheet" href="style_ie.css" type="text/css" media="screen, projection" /><![endif]-->
    </head>
    <body>
    <div id="wrapper">

	<div id="header"> <div id="containertop">
Please enter your username and password to login to chat application. 
Don't have username?  Then, please <s:link beanclass="action.RegisterActionBean" event="view">
     [ register ]
    </s:link>.
    Alternatively, <s:link beanclass="action.SettingsActionBean" event="view">
<s:param name="sid" value="1"/>[ enter here ]</s:link> to modify erlang chat server settings.
    </div></div>
    <div id="middle"><div id="containerbeige">
    <s:errors/>
    <s:form beanclass="action.LoginActionBean">
    <div><s:hidden name="loginUrl"/></div>
	<table>
		<tr>
          <td>username:</td>
          <td><s:text name="username"/></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>password:</td>
          <td><s:password name="password"/></td>
           <td><s:submit name="login" value="Login"/></td>
        </tr>
	</table>
</s:form>
</div></div></div>
    </body>
  </html>