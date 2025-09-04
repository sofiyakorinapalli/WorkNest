<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="_header.jsp" />

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">

<div class="container my-5">
    <h2 class="mb-4">My Tasks</h2>

    <c:if test="${empty tasks}">
        <div class="alert alert-info">No tasks assigned to you yet.</div>
    </c:if>

    <div class="row">
        <c:forEach var="task" items="${tasks}">
            <div class="col-md-6 mb-4">
                <div class="card shadow-sm">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h5 class="card-title mb-0">${task.title}</h5>
                        <span class="badge 
                            ${task.status=='PENDING' ? 'bg-warning' : ''}
                            ${task.status=='IN_PROGRESS' ? 'bg-primary' : ''}
                            ${task.status=='COMPLETED' ? 'bg-success' : ''}
                            ${task.status=='DELAYED' ? 'bg-danger' : ''}">
                            ${task.status}
                        </span>
                    </div>
                    <div class="card-body">
                        <p>${task.description}</p>
                        <p><strong>Start Date:</strong> ${task.startDate}</p>
                        <p><strong>Due Date:</strong> ${task.dueDate}</p>

                        <!-- Status Update Form -->
                        <form action="/user/tasks/updateStatus" method="post" class="mb-3 d-flex gap-2">
                            <input type="hidden" name="taskId" value="${task.id}" />
                            <select name="status" class="form-select">
                                <option value="IN_PROGRESS" ${task.status=='IN_PROGRESS' ? 'selected' : ''}>In Progress</option>
                                <option value="COMPLETED" ${task.status=='COMPLETED' ? 'selected' : ''}>Completed</option>
                            </select>
                            <button type="submit" class="btn btn-primary">Update</button>
                        </form>

                        <!-- Comments Section -->
                        <h6>Comments:</h6>
                        <div class="mb-2" style="max-height:150px; overflow-y:auto;">
                            <c:forEach var="comment" items="${comments_${task.id}}">
                                <div class="border p-2 mb-1 rounded">
                                    <strong>${comment.user.name}:</strong> ${comment.content}
                                </div>
                            </c:forEach>
                        </div>

                        <!-- Add Comment Form -->
                        <form action="/user/tasks/addComment" method="post" class="d-flex gap-2">
                            <input type="hidden" name="taskId" value="${task.id}" />
                            <input type="text" name="content" class="form-control" placeholder="Add a comment" required />
                            <button type="submit" class="btn btn-success">Comment</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
