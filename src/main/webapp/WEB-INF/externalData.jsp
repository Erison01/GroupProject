<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>External Data</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
  <!-- Search bar -->
  <div class="mb-4">
    <input type="text" class="form-control" id="searchInput" placeholder="Search for titles...">
  </div>

  <!-- Display data -->
  <div class="row" id="dataContainer">
    <c:forEach var="item" items="${apiData}">
      <div class="col-md-4 mb-4 data-item">
        <div class="card">
          <img src="${item.image}" class="card-img-top" alt="${item.title}">
          <div class="card-body">
            <h5 class="card-title">${item.title}</h5>
            <p class="card-text">${item.description}</p>
            <pre>${item}</pre>
            <a href="#" class="btn btn-primary">Add to Favorites</a>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
  // Search functionality
  $('#searchInput').on('keyup', function() {
    const searchTerm = $(this).val().toLowerCase();
    $('.data-item').filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(searchTerm) > -1)
    });
  });

</script>
</body>
</html>
