export interface Http {
    get<T>(url: string): Promise<T>
}

export class NetworkHttp implements Http {
    async get<T>(url: string): Promise<T> {
        const response = await fetch(url)
        return await response.json() as Promise<T>
    }

}