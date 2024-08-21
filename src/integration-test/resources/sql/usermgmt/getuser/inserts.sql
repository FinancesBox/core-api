INSERT INTO users(id, name, email, password, created_at, updated_at)
VALUES ('63ce9f6e-3cd1-41a9-af61-e1c232692aea', 'fakeUser01', 'fake@financesbox.com', 'fake', now(), now());
INSERT INTO users_roles(id, user_id, role_id, created_at, updated_at)
VALUES ('63ce9f6e-3cd1-41a9-af61-e1c232692aea', '63ce9f6e-3cd1-41a9-af61-e1c232692aea', 1, now(), now());