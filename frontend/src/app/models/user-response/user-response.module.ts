import { Boite } from "../boite/boite.module";

export interface Reservation {
  id: number;
  boite: Boite;
  utilisateur: {
    id: number;
    nom: string;
    prenom: string;
    mail: string;
    username: string;
  };
  reservation: number;
}

export interface UserResponse {
  id: number;
  nom: string;
  prenom: string;
  mail: string;
  username: string;
  reservations: Reservation[];
}
