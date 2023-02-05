import { Router, ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})


export class AppComponent {
  title = 'logAp-CRUD';
  constructor(private router: Router,private route: ActivatedRoute){

  }

  estoqueLink(){
    this.router.navigate(["/estoque"], {relativeTo :this.route});
  }
  frasesLink(){
    this.router.navigate(["/frases"], {relativeTo :this.route});
  }
}


