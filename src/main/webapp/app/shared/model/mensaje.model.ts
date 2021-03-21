import { IUsuario } from 'app/shared/model/usuario.model';
import { ITema } from 'app/shared/model/tema.model';

export interface IMensaje {
  id?: number;
  body?: string;
  usuario?: IUsuario;
  tema?: ITema;
}

export class Mensaje implements IMensaje {
  constructor(public id?: number, public body?: string, public usuario?: IUsuario, public tema?: ITema) {}
}
