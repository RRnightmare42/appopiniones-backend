import { Moment } from 'moment';
import { ITema } from 'app/shared/model/tema.model';
import { IMensaje } from 'app/shared/model/mensaje.model';

export interface IUsuario {
  id?: number;
  username?: string;
  password?: string;
  photoContentType?: string;
  photo?: any;
  creationDate?: Moment;
  messages?: number;
  temas?: ITema[];
  mensajes?: IMensaje[];
}

export class Usuario implements IUsuario {
  constructor(
    public id?: number,
    public username?: string,
    public password?: string,
    public photoContentType?: string,
    public photo?: any,
    public creationDate?: Moment,
    public messages?: number,
    public temas?: ITema[],
    public mensajes?: IMensaje[]
  ) {}
}
