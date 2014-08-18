<!--
 !Vsevolod Geraskin
 !Final Project
-->
<%@include file="common/taglibs.jsp"%>
 <p><strong>Connected clients:</strong><br>click on connected user to whisper</p>
 	<c:if test="${actionBean.connected}"> 
    <c:forEach var="client" items="${actionBean.clients}">
    <s:link beanclass="action.NotifyListActionBean"
          event="whisper">
          <s:param name="toclient" value="${fn:escapeXml(client)}"/>
          [ ${fn:escapeXml(client)} ]
        </s:link>
    <br>
    </c:forEach>
    </c:if>