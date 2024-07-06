import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import * as THREE from 'three';
import { STLLoader } from 'three/examples/jsm/loaders/STLLoader.js'
import {
  AmbientLight,
  BufferGeometry,
  Mesh,
  MeshPhongMaterial,
  NormalBufferAttributes,
  Object3DEventMap,
  Vector3
} from "three";


@Component({
  selector: 'app-elevator-model',
  standalone: true,
  imports: [],
  templateUrl: './elevator-model.component.html',
  styleUrl: './elevator-model.component.scss'
})
export class ElevatorModelComponent implements OnInit, AfterViewInit {
  @ViewChild('rendererContainer') rendererContainer!: ElementRef;

  private scene!: THREE.Scene;
  private camera!: THREE.PerspectiveCamera;
  private renderer!: THREE.WebGLRenderer;
  private mesh!: Mesh<BufferGeometry<NormalBufferAttributes>, MeshPhongMaterial, Object3DEventMap>;
  public constructor() {}

  ngOnInit(): void {
    //this.initThreeJs();
  }

  ngAfterViewInit(): void {
    this.initThreeJs();
  }

  public initThreeJs(): void {
    const container = this.rendererContainer.nativeElement;

    this.scene = new THREE.Scene();
    this.camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    this.renderer = new THREE.WebGLRenderer({ antialias: true });
    this.renderer.setSize(window.innerWidth, window.innerHeight);
    this.renderer.setClearColor(0xffffff);
    this.camera.position.set(0, 0, 10);
    container.appendChild(this.renderer.domElement);

    const light: AmbientLight = new THREE.AmbientLight(0x404040); // soft white light
    this.scene.add(light);

    const directionalLight = new THREE.DirectionalLight(0xffffff, 1);
    directionalLight.position.set(0, 1, 1).normalize();
    this.scene.add(directionalLight);

    this.loadModel();

    window.addEventListener('resize', this.onWindowResize.bind(this));
    this.animate();
  }

  private loadModel(): void {
    // Load STL model
    const stlLoader = new STLLoader();
    stlLoader.load('assets/models/elevator-model.stl', (geometry: BufferGeometry) => {
      const material: MeshPhongMaterial = new THREE.MeshPhongMaterial({ color: 0x555555 });
      this.mesh = new THREE.Mesh(geometry, material);
      this.scene.add(this.mesh);
      console.log("Loaded Model Successfully", this.mesh)

      this.mesh.geometry.center();


      // Ustawienie pozycji kamery na wprost modelu
      const box = new THREE.Box3().setFromObject(this.mesh);
      const center = box.getCenter(new THREE.Vector3());
      const size = box.getSize(new THREE.Vector3());

      this.mesh.position.set(0-size.x/10 + 40, 0-size.y/10, 0-size.z/10);
      this.mesh.rotateX(180);
      this.mesh.rotateY(-50);
      this.mesh.rotateZ(60);
      const newScaleSize = new Vector3(1.5, 1.5, 1.5);
      this.mesh.scale.set(newScaleSize.x, newScaleSize.y, newScaleSize.z);

      this.camera.position.set(center.x -100, center.y + 200, size.z * 2);
      this.camera.lookAt(center);
    });
  }

  private animate(): void  {
    requestAnimationFrame(this.animate.bind(this));
    // TODO : ANIMATION
    this.mesh?.rotateZ(0.005);
    this.renderer.render(this.scene, this.camera);
  };

  private onWindowResize(): void {
    this.camera.aspect = window.innerWidth / window.innerHeight;
    this.camera.updateProjectionMatrix();
    this.renderer.setSize(window.innerWidth, window.innerHeight);
  }
}
