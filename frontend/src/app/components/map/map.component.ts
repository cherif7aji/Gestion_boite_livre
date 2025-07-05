import { Component, OnInit } from '@angular/core';
import { BoiteService } from '../../services/boite.service';
import { Boite } from '../../models/boite/boite.module';
import { Map } from 'ol'; // Import de la carte
import TileLayer from 'ol/layer/Tile'; // Import de la couche de tuiles
import OSM from 'ol/source/OSM'; // Import de la source OpenStreetMap
import View from 'ol/View'; // Import de la vue de la carte
import { fromLonLat } from 'ol/proj'; // Import de la fonction pour convertir les coordonnées
import Overlay from 'ol/Overlay'; // Import explicite de Overlay

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  boites: Boite[] = [];
  map!: Map;

  constructor(private boiteService: BoiteService) {}

  ngOnInit(): void {
    this.boiteService.getBoites().subscribe(boites => {
      this.boites = boites;
      this.initMap();
    });
  }

  initMap(): void {
    this.map = new Map({
      target: 'map',
      layers: [new TileLayer({ source: new OSM() })],
      view: new View({
        center: fromLonLat([0.701013, 47.354670]), // Centré sur Tours
        zoom: 10
      })
    });

    this.boites.forEach(boite => {
      const [lat, lng] = boite.pointGeo.split(',').map(parseFloat);
      const marker = new Overlay({
        position: fromLonLat([lng, lat]),
        element: this.createMarkerElement(boite)
      });
      this.map.addOverlay(marker);
    });
  }

  createMarkerElement(boite: Boite): HTMLElement {
    const element = document.createElement('div');
    element.className = 'marker'; // Classe CSS personnalisée pour le marker
    element.innerHTML = `
      <div class="marker-icon" onclick="window.location.href='/boite-detail/${boite.id}'" style="cursor: pointer; color: #007bff; font-size: 17px;">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-geo-alt-fill" viewBox="0 0 16 16">
          <path d="M8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10m0-7a3 3 0 1 1 0-6 3 3 0 0 1 0 6"/>
        </svg>
      </div>

      <div class="boite-info">
        <h5 onclick="window.location.href='/boite-detail/${boite.id}'" style="cursor: pointer; color: #007bff; font-size: 12px; font-weight: bold;">
          ${boite.nom}
        </h5>
      </div>


    `;
    return element;
  }
}
