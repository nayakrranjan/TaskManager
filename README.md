# Task Approval System API Documentation
# **Authentication Endpoints**

## **Register a new user:** `POST /api/V1/auth/register`

**Request Body:**

```json
{
  "name": "nayakrranjan",
  "email": "nayakrranjan@gmail.com",
  "password": "demoPassword1@"
}
```

**Response:**

```json
{
	"id": 1001
  "name": "nayakrranjan",
  "email": "nayakrranjan@gmail.com",
}
```

## Login: `POST /api/V1/auth/login`

**Request Body:**

```json
{
  "email": "nayakrranjan@gmail.com",
	"password": "demoPassword1@"
}
```

**Response:**

```json
{
	"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
	"userId": 1001,
	"name": "nayakrranjan",
	"email": "nayakrranjan@gmail.com"
}
```

# **User Endpoints**

## **Get current user:** `GET /api/V1/users/me`

**Headers:**
`Authorization: Bearer {jwt_token}`

**Response:**

```json
{
	"id": 1001
  "name": "nayakrranjan",
  "email": "nayakrranjan@gmail.com",
}
```

## Get all users: `GET /api/V1/users`

**Headers:**
`Authorization: Bearer {jwt_token}`

**Response:**

```json
[
	{
		"id": 1001
	  "name": "nayakrranjan",
	  "email": "nayakrranjan@gmail.com",
	},
	{
		"id": 1002
	  "name": "dibyaranjan",
	  "email": "dibyaranjan@gmail.com",
	},
	{
		"id": 1002
	  "name": "niharnayak",
	  "email": "nihar@gmail.com",
	}
]
```

## Get potential approvers: `GET /api/V1/users/potential-approvers`

**Headers:**
`Authorization: Bearer {jwt_token}`

**Response:**

```json
[
	{
		"id": 1002
	  "name": "dibyaranjan",
	  "email": "dibyaranjan@gmail.com",
	},
	{
		"id": 1003
	  "name": "niharnayak",
	  "email": "nihar@gmail.com",
	}
]
```

# **Task Endpoints**

## **Create a new task:** `POST /api/V1/tasks`

**Headers:**
`Authorization: Bearer {jwt_token}`

**Request Body:**

```json
{
    "title": "New Project Proposal",
    "description": "Proposal for the new client project",
    "approverIds": [1002,1003,1004]
}
```

**Response:**

```json
{
	"id": 1,
	"title": "New Project Proposal",
	"description": "Proposal for the new client project",
	"status": "PENDING",
	"createdAt": "2025-04-04T10:30:00"
	"completedAt": null,
	"creatorId": 1001,
	"creatorName": "nayakrranjan"
	"approvers": []
}
```

## **Get task by ID:** `GET /api/V1/tasks/{taskId}`

**Headers:**`Authorization: Bearer {jwt_token}`

**Responce:**

```json
{
	"id": 1,
	"title": "New Project Proposal",
	"description": "Proposal for the new client project",
	"status": "PENDING",
	"createdAt": "2025-04-04T10:30:00"
	"completedAt": null,
	"creatorId": 1001,
	"creatorName": "nayakrranjan"
	"approvers": [1003,1004]
}
```

## Get current user’s task: `GET /api/V1/tasks/my-tasks`

**Headers:**`Authorization: Bearer {jwt_token}`

**Responce:**

```json
[
	{
		"id": 1,
		"title": "New Project Proposal",
		"description": "Proposal for the new client project",
		"status": "PENDING",
		"createdAt": "2025-04-04T10:30:00"
		"completedAt": null,
		"creatorId": 1001,
		"creatorName": "nayakrranjan"
		"approvers": [1003,1004]
	}
]
```

## **Get tasks awaiting current user's approval:** `GET /api/V1/tasks/pending-approvals`

**Headers:**`Authorization: Bearer {jwt_token}`

**Response**

```json
[
	{
		"id": 2,
		"title": "Budget Approval",
		"description": "Proposal for new budgett",
		"status": "PENDING",
		"createdAt": "2025-04-04T10:30:00"
		"completedAt": null,
		"creatorId": 1003,
		"creatorName": "niharnayak"
		"approvers": [1004]
	}
]
```

# **Approval Endpoints**

## **Approve or reject a task:** `POST /api/V1/approvals/tasks/{taskId}`

**Headers:**`Authorization: Bearer {jwt_token}`

**Request Body:**

```json
{
  "approved": true,
  "comment": "Looks good to me!"
}
```

**Responce:**

```json
{
  "id": 5,
  "taskId": 2,
  "taskTitle": "New Project Proposal",
  "approverId": 1002,
  "approverName": "dibyaranjan",
  "status": "APPROVED",
  "createdAt": "2025-04-04T10:30:00",
  "respondedAt": "2025-04-04T11:45:00",
  "comment": "Looks good to me!"
}

```

## **Get approvals for a task:** `GET /api/V1/approvals/tasks/{taskId}`

**Headers:**`Authorization: Bearer {jwt_token}`

**Response:**

```json
[
  {
    "id": 5,
    "taskId": 1,
    "taskTitle": "New Project Proposal",
    "approverId": 2,
    "approverName": "JaneSmith",
    "status": "APPROVED",
    "createdAt": "2025-04-04T10:30:00",
    "respondedAt": "2025-04-04T11:45:00",
    "comment": "Looks good to me!"
  },
  {
    "id": 6,
    "taskId": 1,
    "taskTitle": "New Project Proposal",
    "approverId": 3,
    "approverName": "BobJohnson",
    "status": "PENDING",
    "createdAt": "2025-04-04T10:30:00",
    "respondedAt": null,
    "comment": null
  }
]
```

## **Get current user’s approvals:** `GET /api/V1/approvals/my-approvals` ****

**Headers:**`Authorization: Bearer {jwt_token}`

**Response:**

```json
[
  {
    "id": 5,
    "taskId": 1,
    "taskTitle": "New Project Proposal",
    "approverId": 1001,
    "approverName": "nayakrranjan",
    "status": "APPROVED",
    "createdAt": "2025-04-04T10:30:00",
    "respondedAt": "2025-04-04T11:45:00",
    "comment": "Looks good to me!"
  }
 ]
```

# **Comment Endpoints**

## **Add a comment to a task:** `POST /api/Vq/comments`

**Headers:**`Authorization: Bearer {jwt_token}`

**Request Body:**

```json
{
  "content": "I have some concerns about the timeline.",
  "taskId": 1
}
```

**Response:**

```json
{
  "id": 1,
  "content": "I have some concerns about the timeline.",
  "createdAt": "2025-04-04T13:15:00",
  "taskId": 1,
  "userId": 1001,
  "userName": "nayakrranjan"
}
```

## **Get comments for a task:** `GET /api/V1/comments/tasks/{taskId}`

**Headers:**`Authorization: Bearer {jwt_token}`

**Response:**

```json
[
  {
    "id": 1,
    "content": "I have some concerns about the timeline.",
    "createdAt": "2025-04-04T13:15:00",
    "taskId": 1,
    "userId": 1001,
    "userName": "nayakrranjan"
  },
  {
    "id": 2,
    "content": "We can adjust the timeline if needed.",
    "createdAt": "2025-04-04T13:20:00",
    "taskId": 1,
    "userId": 1003,
    "userName": "niharnayak"
  }
]

```
