import { IUsuario } from 'app/shared/model/usuario.model';
import { IMensaje } from 'app/shared/model/mensaje.model';

export interface ITema {
  id?: number;
  title?: string;
  description?: string;
  category?: string;
  photoContentType?: string;
  photo?: any;
  usuario?: IUsuario;
  mensajes?: IMensaje[];
}

export class Tema implements ITema {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public category?: string,
    public photoContentType?: string,
    public photo?: any,
    public usuario?: IUsuario,
    public mensajes?: IMensaje[]
  ) {}
}
