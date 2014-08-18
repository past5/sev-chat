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

	<div id="header"><div id="containertop">
To modify erlang server settings, administrator password is required.
</div></div>
<div id="middle"><div id="containerbeige">
    <s:errors globalErrorsOnly="true"/>
    <s:form beanclass="action.SettingsActionBean">
    <div><s:hidden name="sid"/></div>
      <table>
        <tr>
          <td>Server Ip:</td>
          <td><s:text name="chatsettings.serverip"/></td>
          <td><s:errors field="chatsettings.serverip"/></td>
        </tr>
        <tr>
          <td>Server Name:</td>
          <td><s:text name="chatsettings.servername"/></td>
          <td><s:errors field="chatsettings.servername"/></td>
        </tr>
        <tr>
          <td>Server Cookie:</td>
          <td><s:text name="chatsettings.servercookie"/></td>
          <td><s:errors field="chatsettings.servercookie"/></td>
        </tr>
        <tr>
          <td>Administrator Password:</td>
          <td><s:password name="password"/></td>
          <td><s:errors field="password"/></td>
        </tr>
        <tr>
          <td></td>
          <td>
            <s:submit name="save" value="Save"/>
            <s:submit name="cancel" value="Cancel"/>
          </td>
          <td></td>
        </tr>
      </table>
    </s:form>
    </div></div></div>
    </body>
  </html>