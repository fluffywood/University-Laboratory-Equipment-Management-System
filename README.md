# University Equipment Asset Management System

A web-based university equipment and asset management system built with **Spring Boot, Vue, MySQL, and Activiti**. The system provides a complete workflow for equipment registration, multi-stage approval, asset management, and asset value adjustment.

The project adopts a front-end/back-end separated architecture and focuses on digitizing university equipment management workflows, replacing manual registration and approval processes with structured, traceable, and role-based operations.

## Features

* **Equipment Registration** — Register detailed equipment information including department, category, model, quantity, unit price, manufacturer, purchase date, user, and storage location, with automatic business ID generation and form validation.
* **Multi-Level Approval Workflow** — Supports a three-stage approval process for new assets: **Initial Review → Intermediate Review → Final Review**, with rejection and resubmission support.
* **Asset Value Adjustment** — Supports asset value-increase registration with a separate two-stage approval workflow.
* **Approval Tracking** — Records reviewers, timestamps, comments, approval status, and the complete workflow history for each asset.
* **Equipment Management** — Provides paginated queries, multi-condition search, detailed information viewing, editing, deletion, and Excel export.
* **Role-Based Access Control** — Uses Spring Security to manage authentication and permissions for different system roles.
* **Data Validation & Consistency** — Combines front-end validation, back-end parameter validation, database constraints, and transactional operations to maintain data integrity.
* **Workflow Visualization** — Displays the current approval stage and completed workflow steps through an interactive web interface.

## Tech Stack

| Layer            | Technologies                         |
| ---------------- | ------------------------------------ |
| Backend          | Java 8, Spring Boot, Spring Security |
| ORM              | MyBatis-Plus                         |
| Workflow Engine  | Activiti                             |
| Database         | MySQL 8.0                            |
| Connection Pool  | Druid                                |
| Frontend         | Vue 2, Element UI                    |
| State Management | Vuex                                 |
| Routing          | Vue Router                           |
| HTTP Client      | Axios                                |
| Utilities        | Hutool, Moment.js                    |

## System Architecture

The backend follows a layered architecture consisting of **Controller, Service, Mapper, and Entity** layers. RESTful APIs are used for communication between the Vue frontend and Spring Boot backend.

MySQL is used for persistent storage, with core tables covering equipment records, departments, personnel, equipment categories, storage locations, dictionaries, and approval progress. The system uses indexed business identifiers to associate equipment records with their corresponding approval workflows.

Activiti manages the approval process and state transitions, while Spring transaction management ensures that equipment information and approval records remain consistent during multi-table operations.

## Core Workflow

**Equipment Registration → Business ID Generation → Initial Review → Intermediate Review → Final Review → Asset Entry**

Rejected applications can be returned for modification, while the complete approval history—including reviewer, approval time, comments, and status—is retained for traceability.

Asset value adjustment follows a separate workflow:

**Asset Value Adjustment → Initial Review → Final Review → Completion**

## Highlights

The system demonstrates the implementation of a practical enterprise-style information management application involving **workflow orchestration, transactional data processing, role-based access control, relational database design, RESTful API development, and front-end/back-end integration**.

It was developed as part of a university production internship project on **University Equipment Management System Development — Asset Registration, Approval, and Workflow Management**.
