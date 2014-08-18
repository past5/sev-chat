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
Please enter your registration details.
</div></div>
<div id="middle"><div id="containerbeige">
    <s:errors globalErrorsOnly="true"/>
    <s:form beanclass="action.RegisterActionBean">
    <div><s:hidden name="chatuser"/></div>
      <table>
        <tr>
          <td>First Name:</td>
          <td><s:text name="chatuser.firstName"/></td>
          <td><s:errors field="chatuser.firstName"/></td>
        </tr>
        <tr>
          <td>Last Name:</td>
          <td><s:text name="chatuser.lastName"/></td>
          <td><s:errors field="chatuser.lastName"/></td>
        </tr>
        <tr>
          <td>User Name:</td>
          <td><s:text name="chatuser.username"/></td>
          <td><s:errors field="chatuser.username"/></td>
        </tr>
        <tr>
          <td>Password:</td>
          <td><s:password name="chatuser.password"/></td>
          <td><s:errors field="chatuser.password"/></td>
        </tr>
        <tr>
          <td>Confirm password:</td>
          <td><s:password name="confirmPassword"/></td>
          <td><s:errors field="confirmPassword"/></td>
        </tr>
        <tr>
          <td></td>
          <td>
            <s:submit name="register" value="Register"/>
            <s:submit name="cancel" value="Cancel"/>
          </td>
          <td></td>
        </tr>
      </table>
    </s:form>
    </div></div></div>
    </body>
  </html>