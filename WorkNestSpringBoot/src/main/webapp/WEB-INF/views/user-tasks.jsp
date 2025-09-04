<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="_header.jsp"/>

<style>
  body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f4f6f0; /* light olive background */
      margin: 0;
      padding: 20px;
  }

  h2 {
      color: #556b2f; /* dark olive */
      margin-bottom: 20px;
      text-align: center;
  }

  /* Tables */
  table {
      width: 100%;
      border-collapse: collapse;
      background-color: #f0f4e3; /* pale olive table background */
      box-shadow: 0 0 8px rgba(0,0,0,0.1);
      margin-bottom: 25px;
  }

  th, td {
      border: 1px solid #8f9779; /* muted olive border */
      padding: 10px 12px;
      text-align: left;
      vertical-align: middle;
  }

  th {
      background-color: #6b8e23; /* olive green header */
      color: #fff;
      font-weight: bold;
  }

  tr:nth-child(even) {
      background-color: #e6ebd9; /* lighter olive row */
  }

  /* Forms inside table */
  form {
      display: inline-flex;
      align-items: center;
      gap: 5px;
      margin: 0;
  }

  input[type="text"], input[name="content"], select {
      padding: 5px 8px;
      border: 1px solid #8f9779; /* muted olive */
      border-radius: 4px;
      font-size: 13px;
      background-color: #fafaf0; /* very light olive */
  }

  input[type="text"]:focus,
  input[name="content"]:focus,
  select:focus {
      border-color: #556b2f; /* olive focus */
      box-shadow: 0 0 4px rgba(85,107,47,0.3);
      outline: none;
  }

  /* Buttons */
  button {
      background-color: #6b8e23; /* olive green */
      color: #fff;
      border: none;
      padding: 6px 10px;
      border-radius: 4px;
      cursor: pointer;
      font-size: 13px;
      transition: background-color 0.3s, transform 0.2s;
  }

  button:hover {
      background-color: #556b2f; /* darker olive on hover */
      transform: translateY(-1px);
  }

  /* Forward select box */
  select[name="newUserId"] {
      min-width: 100px;
  }

  /* Inputs for comment */
  input[name="content"] {
      min-width: 120px;
  }

  /* Responsive table for small screens */
  @media (max-width: 600px) {
      table, thead, tbody, th, td, tr {
          display: block;
      }

      th {
          position: absolute;
          top: -9999px;
          left: -9999px;
      }

      td {
          border: none;
          padding: 10px 5px;
          position: relative;
          padding-left: 50%;
          margin-bottom: 10px;
      }

      td::before {
          position: absolute;
          top: 10px;
          left: 10px;
          width: 45%;
          white-space: nowrap;
          font-weight: bold;
          content: attr(data-label);
      }

      form {
          flex-direction: column;
          gap: 5px;
          margin-top: 5px;
      }
  }
</style>

<h2>My Tasks</h2>
<table>
  <thead><tr><th>Task ID</th><th>Title</th><th>Status</th><th>Due</th><th>Action</th></tr></thead>
  <tbody>
    <c:forEach var="t" items="${tasks}">
      <tr>
        <td>${t.id}</td>
        <td>${t.title}</td>
        <td>${t.status}</td>
        <td>${t.dueDate}</td>
        <td>
          <!-- Status Update -->
          <form style="display:inline" method="post" action="<c:url value='/user/tasks/status'/>">
            <input type="hidden" name="taskId" value="${t.id}"/>
            <select name="status">
              <option value="PENDING" ${t.status=='PENDING'?'selected':''}>Pending</option>
              <option value="IN_PROGRESS" ${t.status=='IN_PROGRESS'?'selected':''}>In Progress</option>
              <option value="COMPLETED" ${t.status=='COMPLETED'?'selected':''}>Completed</option>
              <option value="DELAYED" ${t.status=='DELAYED'?'selected':''}>Delayed</option>
            </select>
            <button type="submit">Update</button>
          </form>

          <!-- Add Comment -->
          <form style="display:inline" method="post" action="<c:url value='/user/tasks/comment'/>">
            <input type="hidden" name="taskId" value="${t.id}"/>
            <input name="content" placeholder="Add comment"/>
            <button type="submit">Comment</button>
          </form>

          <!-- Forward to another user -->
          <form style="display:inline" method="post" action="<c:url value='/user/tasks/forward'/>">
            <input type="hidden" name="taskId" value="${t.id}"/>
            <select name="newUserId">
                <c:forEach var="u" items="${users}">
                    <c:if test="${u.role ne 'ADMIN'}">
                        <option value="${u.id}">${u.name}</option>
                    </c:if>
                </c:forEach>
            </select>
            <button type="submit">Forward</button>
          </form>

        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>

<jsp:include page="_footer.jsp"/>
