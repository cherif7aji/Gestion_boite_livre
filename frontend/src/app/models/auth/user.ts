export class User {
    username: string|null;
    password: string|null;
  
    constructor(username: string, password: string) {
      this.username = username;
      this.password = password;
    }
  }
  