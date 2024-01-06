/*(C) Gaspay App 2023 */
package com.rancard.dto.request;

import java.io.Serial;
import java.io.Serializable;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EditPasswordDto implements Serializable {

    @Serial private static final long serialVersionUID = 6091522525731613889L;

    @NotNull private String id;

    @NotNull private String password;
}
