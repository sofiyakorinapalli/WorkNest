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

  h2, h3 {
      color: #3b4d34; /* dark olive heading */
      margin-bottom: 15px;
  }

  /* Task Summary Buttons */
  ul {
      list-style: none;
      padding: 0;
      display: flex;
      gap: 10px;
      margin-bottom: 25px;
  }

  ul li {
      background-color: #6b8e23; /* olive green */
      color: #fff;
      padding: 8px 15px;
      border-radius: 5px;
      font-weight: bold;
      display: inline-block;
  }

  /* Tables */
  table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 25px;
      background-color: #f4f7f4; /* light greenish-white */
      box-shadow: 0 0 8px rgba(0,0,0,0.1);
  }

  th, td {
      border: 1px solid #b0bfa0;
      padding: 10px 12px;
      text-align: left;
      vertical-align: middle;
  }

  th {
      background-color: #556b2f; /* dark olive */
      color: #fff;
      font-weight: bold;
  }

  tr:nth-child(even) {
      background-color: #eaf0e6; /* subtle olive shade */
  }

  /* Forms */
  form {
      margin-bottom: 25px;
      padding: 20px;
      background-color: #f4f7f4;
      box-shadow: 0 0 8px rgba(0,0,0,0.1);
      border-radius: 8px;
  }

  form label {
      display: block;
      margin-bottom: 5px;
      font-weight: bold;
      color: #3b4d34; /* dark olive text */
  }

  form input[type="text"],
  form input[type="date"],
  form textarea {
      width: 100%;
      padding: 8px 10px;
      margin-bottom: 15px;
      border: 1px solid #b0bfa0;
      border-radius: 5px;
      font-size: 14px;
      box-sizing: border-box;
      transition: border 0.3s, box-shadow 0.3s;
  }

  form input[type="text"]:focus,
  form input[type="date"]:focus,
  form textarea:focus {
      border-color: #6b8e23;
      box-shadow: 0 0 5px rgba(107,142,35,0.3);
      outline: none;
  }

  form button, form input[type="submit"] {
      background-color: #6b8e23; /* olive green */
      color: #fff;
      border: none;
      padding: 10px 15px;
      border-radius: 5px;
      cursor: pointer;
      font-size: 14px;
      transition: background-color 0.3s, transform 0.2s;
  }

  form button:hover, form input[type="submit"]:hover {
      background-color: #556b2f; /* darker olive on hover */
      transform: translateY(-2px);
  }

  /* Checkbox styling */
  input[type="checkbox"] {
      margin-right: 6px;
  }

  /* Links */
  a {
      color: #6b8e23;
      text-decoration: none;
      font-weight: 500;
  }

  a:hover {
      text-decoration: underline;
      color: #556b2f;
  }

  /* Table action buttons (Delete) */
  table button {
      background-color: #a0522d; /* subtle red-brown */
      color: #fff;
      border: none;
      padding: 4px 8px; /* smaller padding */
      border-radius: 4px;
      cursor: pointer;
      font-size: 12px; /* smaller font */
      transition: background-color 0.3s;
  }

  table button:hover {
      background-color: #804020;
  }
  /* Table actions: Edit + Delete inline */
  table td form {
      display: inline-block; /* keeps button inline with Edit link */
      margin: 0;
      padding: 0;
  }

  table td a {
      margin-right: 10px; /* space between Edit and Delete */
      vertical-align: middle;
  }

  table td button {
      display: inline-block; /* make button inline */
      padding: 4px 8px;
      font-size: 12px;
      margin: 0;
      vertical-align: middle;
      background-color: #a0522d;
      border-radius: 4px;
  }

  table td button:hover {
      background-color: #804020;
  }

  table a {
      color: #6b8e23;
      font-weight: bold;
      margin-right: 8px;
  }

  table a:hover {
      text-decoration: underline;
      color: #556b2f;
  }
</style>

<h2>Admin Dashboard</h2>

<h3>Task Summary</h3>
<ul>
  <li>Pending: ${pendingCount}</li>
  <li>In Progress: ${inProgressCount}</li>
  <li>Completed: ${completedCount}</li>
  <li>Delayed: ${delayedCount}</li>
</ul>

<h3>Users</h3>
<table>
  <thead>
    <tr><th>ID</th><th>Name</th><th>Email</th><th>Role</th><th>Actions</th></tr>
  </thead>
  <tbody>
    <c:forEach var="u" items="${users}">
      <c:if test="${u.role ne 'ADMIN'}">
        <tr>
          <td>${u.id}</td>
          <td>${u.name}</td>
          <td>${u.email}</td>
          <td>${u.role}</td>
          <td>
            <a href="<c:url value='/admin/users/edit?userId=${u.id}'/>">Edit</a>
            <form method="post" action="<c:url value='/admin/users/delete'/>">
                <input type="hidden" name="userId" value="${u.id}"/>
                <button type="submit">Delete</button>
            </form>
          </td>
        </tr>
      </c:if>
    </c:forEach>
  </tbody>
</table>

<h3>Allocate Task (Multiple Users)</h3>
<form method="post" action="<c:url value='/admin/tasks/allocate'/>">
  <label>Title</label>
  <input name="title" required/>

  <label>Description</label>
  <textarea name="description"></textarea>

  <label>Start Date</label>
  <input type="date" name="startDate"/>

  <label>Due Date</label>
  <input type="date" name="dueDate"/>
  
  <label>Assign To:</label><br/>
  <c:forEach var="user" items="${users}">
    <input type="checkbox" name="userIds" value="${user.id}"> ${user.name}<br/>
  </c:forEach>
  
  <button type="submit">Allocate</button>
</form>

<h3>All Tasks</h3>
<table>
  <thead>
    <tr>
      <th>Title</th>
      <th>Assignees</th>
      <th>Status</th>
      <th>Due</th>
      <th>Actions</th>
      <th>Comments</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="t" items="${tasks}">
      <tr>
        <td>${t.title}</td>
        <td>
          <c:forEach var="u" items="${t.users}">
            ${u.name}<br/>
          </c:forEach>
        </td>
        <td>${t.status}</td>
        <td>${t.dueDate}</td>
        <td>
          <a href="<c:url value='/admin/tasks/edit?taskId=${t.id}'/>">Edit</a>
          <form method="post" action="<c:url value='/admin/tasks/delete'/>" style="display:inline;">
            <input type="hidden" name="taskId" value="${t.id}"/>
            <button type="submit">Delete</button>
          </form>
        </td>
        <td>
          <c:forEach var="c" items="${taskComments[t.id]}">
            <strong>${c.user.name}:</strong> ${c.content}<br/>
          </c:forEach>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>

<jsp:include page="_footer.jsp"/>
