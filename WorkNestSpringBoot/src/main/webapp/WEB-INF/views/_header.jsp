<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  /* Container styling */
  .container {
      width: 90%;
      max-width: 1200px;
      margin: 0 auto;
      padding: 10px 0;
  }

  /* Navigation bar */
  nav {
      display: flex;
      justify-content: flex-start;
      align-items: center;
      gap: 15px;
      font-family: Arial, sans-serif;
      font-size: 16px;
  }

  /* Links styling */
  nav a {
      text-decoration: none;
      color: #007bff;
      padding: 5px 10px;
      border-radius: 4px;
      transition: background-color 0.3s;
  }

  nav a:hover {
      background-color: #007bff;
      color: #fff;
  }

  /* Greeting text */
  nav span {
      font-weight: bold;
      color: #333;
  }

  /* Horizontal line */
  hr {
      margin-top: 10px;
      border: none;
      border-top: 1px solid #ccc;
  }
</style>

<div class="container">
  <nav>
    <c:choose>
      <c:when test="${not empty sessionScope.user}">
        <span>Hello, ${sessionScope.user.name} </span>
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
          | <a href="<c:url value='/admin/dashboard'/>">Admin Dashboard</a>
        </c:if>
        
        | <a href="<c:url value='/logout'/>">Logout</a>
      </c:when>
      <c:otherwise>
        <a href="<c:url value='/login'/>">Login</a> | <a href="<c:url value='/register'/>">Register</a>
      </c:otherwise>
    </c:choose>
  </nav>
  <hr/>
</div>
