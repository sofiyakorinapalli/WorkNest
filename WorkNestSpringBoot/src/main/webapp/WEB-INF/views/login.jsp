<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f4f6f0; /* light olive background */
      margin: 0;
      padding: 20px;
  }

  h2 {
      text-align: center;
      color: #556b2f; /* dark olive green */
      margin-bottom: 25px;
  }

  form {
      max-width: 400px;
      margin: 0 auto;
      background-color: #f0f4e3; /* pale olive */
      padding: 30px 25px;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
      display: flex;
      flex-direction: column;
      gap: 15px;
  }

  label {
      font-weight: bold;
      margin-bottom: 6px;
      color: #556b2f; /* dark olive for labels */
  }

  input[type="email"],
  input[type="password"] {
      width: 100%;
      padding: 10px 12px;
      border: 1px solid #8f9779; /* muted olive border */
      border-radius: 5px;
      font-size: 14px;
      box-sizing: border-box;
      transition: border 0.3s, box-shadow 0.3s;
      background-color: #fafaf0; /* very light olive background for input */
  }

  input[type="email"]:focus,
  input[type="password"]:focus {
      border-color: #6b8e23; /* olive green focus */
      box-shadow: 0 0 5px rgba(107,142,35,0.3);
      outline: none;
  }

  button.btn {
      background-color: #6b8e23; /* olive green button */
      color: #fff;
      border: none;
      padding: 12px;
      border-radius: 5px;
      cursor: pointer;
      font-size: 16px;
      transition: background-color 0.3s, transform 0.2s;
      align-self: center;
      width: 50%;
  }

  button.btn:hover {
      background-color: #556b2f; /* darker olive on hover */
      transform: translateY(-2px);
  }

  .error {
      color: #b22222; /* dark red for errors */
      font-size: 13px;
      margin-top: -10px;
      margin-bottom: 10px;
      text-align: center;
  }

  p {
      text-align: center;
      margin-top: 15px;
      font-size: 14px;
  }

  p a {
      color: #6b8e23; /* olive green link */
      text-decoration: none;
  }

  p a:hover {
      text-decoration: underline;
  }
</style>

<h2>Login</h2>

<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>

<form method="post" action="login">
  <label>Email</label>
  <input type="email" name="email" required/>

  <label>Password</label>
  <input type="password" name="password" required/>

  <button class="btn" type="submit">Login</button>
</form>

<p>Don't have an account? <a href="<c:url value='/register'/>">Register</a></p>

<jsp:include page="_footer.jsp"/>
