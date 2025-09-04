<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="_header.jsp"/>

<style>
  body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #e6f0e6; /* light olive background */
      margin: 0;
      padding: 20px;
  }

  h2 {
      color: #3b4d34; /* dark olive heading */
      margin-bottom: 20px;
  }

  form {
      max-width: 600px;
      margin: 0 auto;
      background-color: #f4f7f4; /* light greenish-white */
      padding: 25px 30px;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
      display: flex;
      flex-direction: column;
      gap: 15px;
  }

  label {
      font-weight: bold;
      margin-bottom: 5px;
      color: #3b4d34; /* dark olive text */
  }

  input[type="text"],
  input[type="email"],
  select {
      width: 100%;
      padding: 10px 12px;
      border: 1px solid #b0bfa0;
      border-radius: 5px;
      font-size: 14px;
      box-sizing: border-box;
      transition: border 0.3s, box-shadow 0.3s;
  }

  input[type="text"]:focus,
  input[type="email"]:focus,
  select:focus {
      border-color: #6b8e23; /* olive focus */
      box-shadow: 0 0 5px rgba(107,142,35,0.3);
      outline: none;
  }

  button {
      background-color: #6b8e23; /* olive green */
      color: #fff;
      border: none;
      padding: 12px;
      border-radius: 5px;
      cursor: pointer;
      font-size: 16px;
      transition: background-color 0.3s, transform 0.2s;
      align-self: flex-start;
  }

  button:hover {
      background-color: #556b2f; /* darker olive on hover */
      transform: translateY(-2px);
  }
</style>

<h2>Edit User</h2>
<form method="post" action="<c:url value='/admin/users/edit'/>">
    <input type="hidden" name="id" value="${user.id}"/>

    <label>Name</label>
    <input type="text" name="name" value="${user.name}" required/>

    <label>Email</label>
    <input type="email" name="email" value="${user.email}" required/>

    <label>Role</label>
    <select name="role" required>
        <option value="ADMIN" ${user.role=='ADMIN' ? 'selected' : ''}>ADMIN</option>
        <option value="USER" ${user.role=='USER' ? 'selected' : ''}>USER</option>
    </select>

    <button type="submit">Save Changes</button>
</form>

<jsp:include page="_footer.jsp"/>
