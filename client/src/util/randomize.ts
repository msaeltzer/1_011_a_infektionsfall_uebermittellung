export const randomizeProperties = (keys: any, object: any) => {
    keys.forEach((key: any) => {
      if (typeof key === "string") {
        object[key] =
          Math.random()
            .toString(36)
            .substring(2, 15) +
          Math.random()
            .toString(36)
            .substring(2, 15);
      } else if (typeof key === "object" && key.type === "number") {
        object[key.key] = Math.round((Math.random() + 0.1) * 10000);
      }
    });
  };
  