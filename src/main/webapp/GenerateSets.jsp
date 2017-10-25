<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.String"%>
<html>
<body>
<header>
        <a href="index.jsp">Main</a>
        <a href="GetChain.jsp">Get chain</a>
        <a href="GenerateSets.jsp">Generate sets</a>
</header>
<c:if test="${sessionScope.chain != null}">
    Cool
</c:if>
<%=session.getAttribute("chain")%>
<c:out value='${sessionScope.chain}'/>
</body>
</html>
