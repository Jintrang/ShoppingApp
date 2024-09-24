import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { ProgramComponent } from './app/program/program.component';

bootstrapApplication(ProgramComponent, appConfig)
  .catch((err) => console.error(err));
