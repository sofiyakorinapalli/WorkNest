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
  input[type="date"],
  textarea,
  select {
      width: 100%;
      padding: 10px 12px;
      border: 1px solid #b0bfa0;
      border-radius: 5px;
      font-size: 14px;
      box-sizing: border-box;
      transition: border 0.3s, box-shadow 0.3s;
  }

  textarea {
      resize: vertical;
      min-height: 80px;
  }

  input[type="text"]:focus,
  input[type="date"]:focus,
  textarea:focus,
  select:focus {
      border-color: #6b8e23; /* olive focus */
      box-shadow: 0 0 5px rgba(107,142,35,0.3);
      outline: none;
  }

  select[multiple] {
      height: auto;
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

<h2>Edit Task</h2>
<form method="post" action="<c:url value='/admin/tasks/edit'/>">
  <input type="hidden" name="id" value="${task.id}"/>
  
  <label>Title</label>
  <input name="title" value="${task.title}" required/>
  
  <label>Description</label>
  <textarea name="description">${task.description}</textarea>
  
  <label>Status</label>
  <select name="status">
    <option value="PENDING" ${task.status=='PENDING'?'selected':''}>PENDING</option>
    <option value="IN_PROGRESS" ${task.status=='IN_PROGRESS'?'selected':''}>IN_PROGRESS</option>
    <option value="COMPLETED" ${task.status=='COMPLETED'?'selected':''}>COMPLETED</option>
  </select>
  
  <label>Start Date</label>
  <input type="date" name="startDate" value="${task.startDate}"/>
  
  <label>Due Date</label>
  <input type="date" name="dueDate" value="${task.dueDate}"/>
  
  <label>Assign To</label>
  <select name="userIds" multiple="multiple" size="5">
    <c:forEach var="u" items="${users}">
      <c:set var="selected" value="false" />
      <c:forEach var="tu" items="${task.users}">
        <c:if test="${tu.id == u.id}">
          <c:set var="selected" value="true" />
        </c:if>
      </c:forEach>
      <option value="${u.id}" ${selected ? 'selected' : ''}>${u.name}</option>
    </c:forEach>
  </select>
  
  <button type="submit">Save Changes</button>
</form>

<jsp:include page="_footer.jsp"/>
