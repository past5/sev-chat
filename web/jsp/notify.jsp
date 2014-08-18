<!--
 !Vsevolod Geraskin
 !Final Project
-->
<%@include file="common/taglibs.jsp"%>
<c:if test="${!actionBean.connected}"> 
<strong><font class="notconnected">not connected</font></strong> - click connect button after Erlang sevchat_server is started
</c:if>
<c:if test="${actionBean.connected}"> 
<strong><font class="connected">connected</font></strong>
</c:if>
<hr>
    <c:forEach var="message" items="${actionBean.messages}">
    <c:if test="${message.usernameto != null}"> 
   	<p><i><strong>${fn:escapeXml(message.usernamefrom)}</strong> whispers to 
   	<strong>${fn:escapeXml(message.usernameto)}</strong> at (${fn:escapeXml(message.messagetime)}) 
   	${fn:escapeXml(message.chatmessage)}</i></p>
	</c:if> 
	<c:if test="${message.usernameto == null}"> 
   	<p><strong>${fn:escapeXml(message.usernamefrom)}</strong> says at (${fn:escapeXml(message.messagetime)})
   	${fn:escapeXml(message.chatmessage)}</p>
	</c:if>
    </c:forEach>