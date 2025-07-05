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

export interface BoiteResponse {
  
    id:Number;
    quantite: Number;
    nom: String;
    description: String;
    pointGeo: String;
    reservations: Reservation[];

}